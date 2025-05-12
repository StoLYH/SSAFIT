package com.ssafy.mvc.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dao.BoardDao;
import com.ssafy.mvc.model.dto.BoardFile;
import com.ssafy.mvc.model.dto.ColBoard;
import com.ssafy.mvc.model.dto.SearchCondition;

@Service
public class BoardServiceImpl implements BoardService{

	@Value("${file.upload.directory}")
	private String uploadDir;				// application.properties에 존재
	
	BoardDao boardDao;
	
	@Autowired
	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}


	@Override
	public List<ColBoard> getCategoryBoard(int categoryNum) {
		return boardDao.getCategory(categoryNum);
	}


	@Override
	public int intsertCategoryBoard(ColBoard colBoard) {
		int result = boardDao.insertBoard(colBoard);	// 게시물 등록
		int primarykey = colBoard.getColboardId();		// 기본키
		System.out.println(primarykey + "기본키 확인");	
		
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
				System.out.println(fileInsertResult + " fileInsertResult");
				if (fileInsertResult != 1) {
					return -1;	// 파일 처리 실패시 예외처리
				}
			}
		}
		
		return 1;	// 실행완료.
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
		
		return boardDao.getBoard(colboardId);
	}


	@Override
	public int updateBoard(ColBoard colBoard) {
		return boardDao.update(colBoard);
	}


	@Override
	public int deleteBoard(int colboardId) {
		// 게시판 삭제시 파일 연쇄 삭제
		return boardDao.delete(colboardId);
	}


	@Override
	public List<ColBoard> getBoardlistByUser(String userId) {
		return boardDao.getBoardByUser(userId);
	}


	@Override
	public List<ColBoard> getSearchBoard(SearchCondition condition) {
		return boardDao.search(condition);
	}

	
}
