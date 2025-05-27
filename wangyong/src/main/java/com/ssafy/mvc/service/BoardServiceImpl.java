package com.ssafy.mvc.service;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.ssafy.mvc.model.dto.*;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.exception.BoardException;
import com.ssafy.mvc.model.dao.BoardDao;
import com.ssafy.mvc.model.dao.FileDao;

@Service
public class BoardServiceImpl implements BoardService{

	@Value("${file.upload.directory}")
	private String uploadDir;				// application.properties에 존재
	
	BoardDao boardDao;
	FileDao fileDao;

	@Autowired
	private EmbeddingService embeddingService;
	
	
	@Autowired
	public BoardServiceImpl(BoardDao boardDao, FileDao fileDao) {
		this.boardDao = boardDao;
		this.fileDao = fileDao;
	}


	@Override
	public List<ColBoard> getCategoryBoard(int categoryNum) {
		List<ColBoard> list = boardDao.getCategory(categoryNum);
		
		for (ColBoard c: list) {
			int id = c.getColboardId();	// 게시판 기본키
			List<BoardFile> fileList = fileDao.getFiles(id);		// 게시판 기본키 이용해 해당 파일을 모두 가져온다.
			c.setBoardFiles(fileList);	
		}
		return list;
	}


	@Override
	public int intsertCategoryBoard(ColBoard colBoard) {
		int result = boardDao.insertBoard(colBoard);
		int primarykey = colBoard.getColboardId();
		
		// 첨부파일이 없는경우.
		List<MultipartFile> attachList = colBoard.getAttach();
		if (attachList == null) {
			return result;
		}
		
		for (MultipartFile attach : attachList) {
			if (!attach.isEmpty()) {
				String originalName = attach.getOriginalFilename();
				long fileSize = attach.getSize();	// byte 크기
				String uploadName = generateUniqueName(originalName);
				File dirFile = new File(uploadDir);	// 저장할 경로 설정
				
				if (!dirFile.exists()) {
					dirFile.mkdirs();		// 해당 디렉토리 없으면 만들기
				}
				File file = new File(dirFile, uploadName);	// 저장경로 + 이름 설정
				
				// 1. 서버에 저장 (나중에 처리)
				try {
					attach.transferTo(file);
				} catch (Exception e) {		// 예외처리 고민 해봐야 할듯.....
					e.printStackTrace();
				}
			
				
				// 2. 디비에 저장
				BoardFile boardFile = new BoardFile();
				// no, original_name, upload_name, file_size
				boardFile.setColboardId(primarykey);		// 외래키
				boardFile.setOriginalName(originalName);
				boardFile.setUploadName(uploadName);
				boardFile.setFileSize(fileSize);
				
				
				int fileInsertResult = boardDao.insertBoardFile(boardFile);
				if (fileInsertResult != 1) {
					return -1;	// 파일 처리 실패시 예외처리
				}
			}
		}
		
		// === Embedding 저장 ===
		String rawText = colBoard.getTitle() + " " + colBoard.getContent();
		String cleanText = Jsoup.parse(rawText).text();  			// HTML 태그 제거
		
		List<Float> embedding = embeddingService.getEmbedding(cleanText);
		
		
		byte[] embeddingBytes = floatListToByteArray(embedding);
		boardDao.insertOrUpdateEmbedding(primarykey, embeddingBytes);
		
		return 1;
	}
	
	private byte[] floatListToByteArray(List<Float> floatList) {
	    ByteBuffer buffer = ByteBuffer.allocate(floatList.size() * 4);
	    for (Float f : floatList) {
	        buffer.putFloat(f);
	    }
	    return buffer.array();
	}
	
	private String generateUniqueName(String originalName) {
		String timeStr = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String uniqueStr = UUID.randomUUID().toString().substring(0, 8);
		int index = originalName.lastIndexOf(".");
		String extName = "";
		if (index != -1) {
			extName = originalName.substring(index);
		}
		return timeStr + "_" + uniqueStr + extName;
	}
	


	

	@Override
	public ColBoard getOneBoard(int colboardId) {
		// 조회를 하였기 때문에 조회수 1 증가해야 한다.
		boardDao.upBoardCnt(colboardId);
		
		List<BoardFile> File = fileDao.getFiles(colboardId);  // 해당하는 게시판이 가지고 있는 파일정보
		
		ColBoard board = boardDao.getBoard(colboardId);				
		board.setBoardFiles(File);
		
		return board;
	}
	
	@Override
	public ColBoard getOneBoardWithoutCnt(int colboardId) {
		
		List<BoardFile> File = fileDao.getFiles(colboardId);  // 해당하는 게시판이 가지고 있는 파일정보
		
		ColBoard board = boardDao.getBoard(colboardId);				
		board.setBoardFiles(File);
		
		return board;
	}
	


	@Override
	public int updateBoard(ColBoard colBoard) {
		List<BoardFile> fileList = fileDao.getFiles(colBoard.getColboardId());
		// 기존파일 전체삭제 (서버,db)
		boolean result1 = deleteFileFromServer(fileList);				// 서버
		int result2 = fileDao.deleteFile(colBoard.getColboardId());		// db(파일만 삭제)
		
		if (result1 == true) {
			System.out.println("게시물 서버삭제완료");
		}
		if (result2 == 1) {
			System.out.println("게시물 db삭제완료");
		}
		
		// 서버단, 파일 재 업로드
		forupdate(colBoard);
		
		// db처리
		int updateResult = boardDao.update(colBoard);
		
		// === Embedding 저장 ===
		String text = colBoard.getTitle() + " " + colBoard.getContent();
		List<Float> embedding = embeddingService.getEmbedding(text);
		byte[] embeddingBytes = floatListToByteArray(embedding);

		
		boardDao.insertOrUpdateEmbedding(colBoard.getColboardId(), embeddingBytes);
		return updateResult;
	}
	
	@Override
	public boolean forupdate(ColBoard colBoard) {		// 업데이트 정보를 내포

		int primarykey = colBoard.getColboardId();		// 기본키
		
		// 첨부파일이 없는경우.
		List<MultipartFile> attachList = colBoard.getAttach();
		if (attachList == null) {
			return true;
		}
		
		for (MultipartFile attach : attachList) {
			if (!attach.isEmpty()) {
				String originalName = attach.getOriginalFilename();
				long fileSize = attach.getSize();	// byte 크기
				String uploadName = generateUniqueName(originalName);
				File dirFile = new File(uploadDir);	// 저장할 경로 설정
				
				if (!dirFile.exists()) {
					dirFile.mkdirs();		// 해당 디렉토리 없으면 만들기
				}
				File file = new File(dirFile, uploadName);	// 저장경로 + 이름 설정
				
				// 1. 서버에 저장 (나중에 처리)
				try {
					attach.transferTo(file);
				} catch (Exception e) {		// 예외처리 고민 해봐야 할듯.....
					e.printStackTrace();
				}
			
				
				// 2. 디비에 저장
				BoardFile boardFile = new BoardFile();
				// no, original_name, upload_name, file_size
				boardFile.setColboardId(primarykey);		// 외래키
				boardFile.setOriginalName(originalName);
				boardFile.setUploadName(uploadName);
				boardFile.setFileSize(fileSize);
				
				
				int fileInsertResult = boardDao.insertBoardFile(boardFile);
				if (fileInsertResult != 1) {
					return false;	// 파일 처리 실패시 예외처리
				}
			}
		}
		
		return true;	// 실행완료.
	}
	
	
	// 서버 파일 삭제
	public boolean deleteFileFromServer(List<BoardFile> fileList) {
	    boolean allDeleted = true; // 전체 삭제 성공 여부

	    // 서버에서 제거
	    for (BoardFile f : fileList) {
	    	String uploadName = f.getUploadName();
	        try {
	            Path filePath = Paths.get(uploadDir).resolve(uploadName).normalize();
	            File file = filePath.toFile();

	            if (file.exists()) {
	                boolean deleted = file.delete();
	                if (!deleted) {
	                    System.out.println("파일 삭제 실패: " + uploadName);
	                    allDeleted = false;
	                }
	            } else {
	                System.out.println("파일이 존재하지 않음: " + uploadName);
	                allDeleted = false;
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            allDeleted = false;
	        }
	        System.out.println("파일 삭제 완료.");
	    }

	    return allDeleted;
	}
	


	@Override
	public int deleteBoard(int colboardId) {
		// 보드파일을 가져와야지!!!!!!!!!!!!
		List<BoardFile> fileList = fileDao.getFiles(colboardId);

		if (fileList != null) {
			boolean result = deleteFileFromServer(fileList);
			if (result == true) {
			}
		}

	
		int result = boardDao.deleteEmbedding(colboardId);
		if (result != 1) {
			throw new BoardException("Embedding 삭제시 에러");
		}
	
		
		// 게시판 삭제시 파일 연쇄 삭제
		return boardDao.delete(colboardId);
	}


	@Override
	public List<ColBoard> getBoardlistByUser(String userId) {
		List<ColBoard> list = boardDao.getBoardByUser(userId);
		
		for (ColBoard c: list) {
			int id = c.getColboardId();	// 게시판 기본키
			List<BoardFile> fileList = fileDao.getFiles(id);		// 게시판 기본키 이용해 해당 파일을 모두 가져온다.
			c.setBoardFiles(fileList);	
		}
		
		return list;
	}


	@Override
	public List<ColBoard> getSearchBoard(SearchCondition condition) {
		// 기본키에 해당하는 파일 다 찾아와서 반환 해줘야 함 
		List<ColBoard> list = boardDao.search(condition);
		for (ColBoard c: list) {
			int id = c.getColboardId();	// 게시판 기본키
			List<BoardFile> fileList = fileDao.getFiles(id);		// 게시판 기본키 이용해 해당 파일을 모두 가져온다.
			c.setBoardFiles(fileList);	
		}
				
		return list;
	}


	@Override
	public List<ColBoard> getRecentBoard() {
		return boardDao.getRecentBoardList();
	}

	@Override
	public List<ColBoard> getPopularBoard() {
		return boardDao.getPopularBoardList();
	}

	@Override
	public List<ColBoard> getUserPopularBoard(String userId) {
		return boardDao.getUserPopularBoardList(userId);
	}


	@Override
	public int clickBoardLike(BoardLike boardLike) {
		
		// 사용자 + 게시판 id 존재하는지 확인
		int result = boardDao.confirmClick(boardLike);
		
		if (result == 1) {
			// 존재하면 -> 기존 것 삭제
			return boardDao.deleteClick(boardLike);
		} else if (result == 0) {
			// 존재안하면 -> 기존것 추가 
			return boardDao.insertClick(boardLike);
		} 
	
		
		// 오류
		return -1;
	}


	@Override
	public int getLikeCount(int colboardId) {
		try {
			return boardDao.getLike(colboardId);
		} catch(Exception e) {
			throw new BoardException("좋아요 조회 시 에러");
		}
	}


	@Override
	public List<List<Integer>> getBestWriterBoards() {
		// 인기 작가 3명 가져와 (추천수 한달간 종합 랭킹 3위까지)
		List<String> BestWriter = boardDao.getMonthBestWriter();
		if (BestWriter == null) {
			throw new BoardException("인기 작가 조회시 에러");
		} else {
			List<List<Integer>> bestList = new ArrayList<>();
			// 인기 작가마다 추천 가장 많이 많은 게시물 3개 가져와
			for (int i = 0; i < 3; i++) {
				List<Integer> list = boardDao.getBestBoard(BestWriter.get(i));
				bestList.add(list);
			}
			return bestList;
		}
	}


	@Override
	public List<ColBoard> getTop3(int colboardId) {
		
		// 1. 작가를 찾자
		ColBoard board = boardDao.getBoard(colboardId);
		String writer = board.getUserId();
		
		// 2. 해당 작가의 조회수 top3를 게시판 정보를 List로 가져오자		
		return boardDao.getWriterTop3(writer);
	}

	private List<Float> byteArrayToFloatList(byte[] bytes) {
		List<Float> floatList = new ArrayList<>();
		for (int i = 0; i < bytes.length; i += 4) {
			int intBits = ((bytes[i] & 0xFF) << 24) |
						 ((bytes[i + 1] & 0xFF) << 16) |
						 ((bytes[i + 2] & 0xFF) << 8) |
						 (bytes[i + 3] & 0xFF);
			floatList.add(Float.intBitsToFloat(intBits));
		}
		return floatList;
	}

	private double cosineSimilarity(List<Float> v1, List<Float> v2) {
		if (v1 == null || v2 == null || v1.size() != v2.size()) {
			return 0.0;
		}
		
		double dotProduct = 0.0;
		double norm1 = 0.0;
		double norm2 = 0.0;
		
		for (int i = 0; i < v1.size(); i++) {
			dotProduct += v1.get(i) * v2.get(i);
			norm1 += v1.get(i) * v1.get(i);
			norm2 += v2.get(i) * v2.get(i);
		}
		
		if (norm1 == 0.0 || norm2 == 0.0) {
			return 0.0;
		}
		
		return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
	}

	public List<ColBoard> recommendByEmbedding(String question, int topN) {
		List<Float> qVec = embeddingService.getEmbedding(question);			// 질문 임베딩 백터화

		// 백터주석
//		System.out.println("qVec 길이: " + qVec.size());
//		System.out.println("qVec 값 샘플: " + qVec.subList(0, Math.min(5, qVec.size())));
		
		List<Embedding> all = boardDao.getAllEmbeddings();					// DB에서 모든 게시글의 임베딩 정보를 불러옵니다.
		List<ColBoard> allBoards = new ArrayList<>();
		List<Double> similarities = new ArrayList<>();
		
		for (Embedding e : all) {
			byte[] vecBytes = e.getEmbedding();
			List<Float> vec = byteArrayToFloatList(vecBytes);  // byte[]를 List<Float>로 변환
			
			// 벡터 크기 검증
			if (vec.size() != qVec.size()) {
				System.out.println("경고: 벡터 크기가 일치하지 않습니다. ID: " + e.getColboardId() + 
					", 예상 크기: " + qVec.size() + ", 실제 크기: " + vec.size());
				continue;
			}
			
			double similarity = cosineSimilarity(qVec, vec);
			
			// System.out.println("게시글 ID: " + e.getColboardId() + ", 유사도: " + similarity);
			
			similarities.add(similarity);
			allBoards.add(boardDao.getBoard(e.getColboardId()));
		}
		
		// 유사도 기준 내림차순 정렬 후 상위 N개 반환
		List<Integer> indices = new ArrayList<>();
		for (int i = 0; i < similarities.size(); i++) {
			indices.add(i);
		}
		
		indices.sort((a, b) -> Double.compare(similarities.get(b), similarities.get(a)));
		
		List<ColBoard> result = new ArrayList<>();
		for (int i = 0; i < Math.min(topN, indices.size()); i++) {
			result.add(allBoards.get(indices.get(i)));
		}
		
		return result;
	}
}
