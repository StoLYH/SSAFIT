package com.ssafy.mvc.controller;
import java.security.Provider.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.exception.BoardException;
import com.ssafy.mvc.model.dto.ColBoard;
import com.ssafy.mvc.model.dto.SearchCondition;
import com.ssafy.mvc.service.BoardService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/board")
public class BoardController {
	// 서버오류 500 ex, DB 접속실패,SQL 문법오류, 자바 내부예외
	
	BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	/**
	 * GET http://localhost:8080/board?key=user_id&word=003&orderBy=view_cnt&orderByDir=desc
	 * 전체 게시글 조회 (메인페이지) + 검색기능
	 * 
	 * user_id(작가), category, content 기준으로 필터링
	 * 조회수, 시간 기준으로 정렬
	 * 	key: user_id(작가), title, content
	 *  word: 검색어
	 *  orderBy: view_cnt(조회수), created_at(시간)
	 *  orderByDir: ASC,DESC
	 *  
	 * 정상 실행 : 200
	 * 데이터가 없는 경우 : 404
	 * 서버오류 : 500
	 * 
	 * get매핑 시 requestBody 사용불가.
	 * 	 */
	@GetMapping()
	public ResponseEntity<?> getMethod1(
			@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "word", required = false) String word,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderByDir", required = false) String orderByDir) {
		
		
		System.out.println("안녕");

		SearchCondition condition = new SearchCondition(key, word, orderBy, orderByDir);
		
		
		try {
			List<ColBoard> list = boardService.getSearchBoard(condition);
			if (!list.isEmpty()) {
				// 정상동작:200
				return ResponseEntity.status(HttpStatus.OK).body(list);
			} else {
				// 데이터가 없는 경우: 404
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
		} catch (Exception e) {
			throw new BoardException("검색시 에러 발생 ");
			// GET http://localhost:8080/board?key=usd&word=user001&orderBy=view_cnt&orderByDir=desc
		}
	}
	
	
	/**
	 *  카테고리별 게시글 전체목록 조회
	 *  (1, '운동/트레이닝'), (2, '재활/통증 관리'), (3, '영양/식단')
	 *  (4, '정신 건강/라이프스타일'), (5, '의학/질환 정보')
	 *  정상 실행 : 200
	 *  데이터가 없는 경우 : 404
	 *  서버오류 : 500 
	 */ 
	@GetMapping("/category/{boardNum}")
	public ResponseEntity<?> getMethod2(@PathVariable("boardNum") int boardNum) {
		try {
			List<ColBoard> list = boardService.getCategoryBoard(boardNum);	
			if (!list.isEmpty()) {
				// 정상동작:200
				return ResponseEntity.status(HttpStatus.OK).body(list);
			} else {
				// 데이터가 없는경우 404 [오류x]
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			// 서버오류 500
			throw new BoardException("카테고리별 조회시 에러 발생");	
		}
	}
	
	
	/**
	 * 디테일 페이지 이동 시, colboard_Id(기본키)를 이용하여 게시물 1개 가져오기
	 * 조회수 1 증가가 일어난다. (service단에서 처리)
	 * 정상 실행 : 200
	 * 데이터가 없는 경우 : 404
	 * 서버오류 : 500 경우
	 */
	@GetMapping({"{colboardId}"})
	public ResponseEntity<?> getMethod3(@PathVariable("colboardId") int colboardId) {
		try {
			ColBoard colBoard = boardService.getOneBoard(colboardId);
			System.out.println(colBoard);
			if (colBoard != null) {	
				// 등록성공 200
				return ResponseEntity.status(HttpStatus.OK).body(colBoard);
			} else {	
				// 데이터가 없는경우 404	[오류x] => 사실 발동 될 일이 없다....
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {	
			throw new BoardException("개별 게시물 조회시 에러 발생");	
			// 서버오류 500
		}
	}
	
	
	/**
	 * user_id(기본키)를 이용하여 마이페이지에서 사용자가 등록한 게시물 조회
	 * 정상 실행 : 200
	 * 데이터가 없는 겨우 : 404
	 * 서버 오류 : 500
	 */
	@GetMapping("user/{userId}")
	public ResponseEntity<?> getMethod4(@PathVariable("userId") String userId) {
		
		try {
			List<ColBoard> list = boardService.getBoardlistByUser(userId);	
			if (!list.isEmpty()) {
				// 정상동작:200
				return ResponseEntity.status(HttpStatus.OK).body(list);
			} else {
				// 데이터가 없는경우 404 [오류x]
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			// 서버오류 500
			throw new BoardException("사용자 게시물 조회시 에러 발생");		
		}
	}
	
	
	
	/**
	 *  카테고리별 게시글 등록 
	 *  (1, '운동/트레이닝'), (2, '재활/통증 관리'), (3, '영양/식단')
	 *  (4, '정신 건강/라이프스타일'), (5, '의학/질환 정보')
	 *  정상 실행 : 201
	 *  서버 오류 : 500
	 */
	@PostMapping
	public ResponseEntity<String> postMethodName(@ModelAttribute ColBoard colBoard) {
		
		try {
			int result = boardService.intsertCategoryBoard(colBoard);
			if(result == 1) {	// 등록성공 201
				return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 등록 되었습니다.");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생: 게시물 등록시 에러 발생");
			}
		} catch (Exception e) {
			// 서버오류 500
			throw new BoardException("게시물 등록시 에러 발생");	
		}
		
	}
	
	
	/**
	 * colboard_id(기본키)와 ColBoard객체를 이용한 게시글 정보 수정
	 * 파일처리 => 전체갱신방식 선택
	 * 기존 게시물의 파일 전체삭제 이후 재 업로드 
	 * 정상 실행 : 200
	 * 서버 오류 : 500
	 */
	@PutMapping("{colboardId}")
	public ResponseEntity<String> postMethodName(@PathVariable("colboardId") int colboardId, @ModelAttribute ColBoard colBoard) {
		
		try {
			colBoard.setColboardId(colboardId);
			int result = boardService.updateBoard(colBoard);
			if (result == 1) {
				return ResponseEntity.status(HttpStatus.OK).body("성공적으로 업데이트 되었습니다");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생: 업데이트 에러 발생");
			}
		} catch (Exception e) {
			throw new BoardException("게시물 업데이트시 에러 발생");	
		}
	}
	
	/**
	 * colboard_id(기본키)를 이용하여 게시물 삭제
	 * 정상 실행 : 200
	 * 서버 오류 : 500
	 */
	@DeleteMapping("{colboardId}")
	public ResponseEntity<String> deleteMethod(@PathVariable("colboardId") int colboardId) {
		try {
			int result = boardService.deleteBoard(colboardId);
			if (result == 1) {
				return ResponseEntity.status(HttpStatus.OK).body("성공적으로 삭제 되었습니다");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생: 게시물 삭제시 예외 발생");
			}
		} catch (Exception e) {
			throw new BoardException("게시물 삭제시 에러 발생");	
		}
	}
	
	
	// 최근에 올라온 칼럼 3개만 가져오도록 Limit 걸어두었다.
	@GetMapping("recent")
	public ResponseEntity<List<ColBoard>> getRecentColumns() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(boardService.getRecentBoard());
		} catch(Exception e) {
			throw new BoardException("최근에 올라온 칼럼 조회시 에러");
		}
	}

	@GetMapping("popular")
	public ResponseEntity<List<ColBoard>> getPopularColumns() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(boardService.getPopularBoard());
		} catch(Exception e) {
			throw new BoardException("인기칼럼 조회시 에러");
		}
	}








}
