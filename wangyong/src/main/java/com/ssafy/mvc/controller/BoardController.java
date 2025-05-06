package com.ssafy.mvc.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.mvc.model.dto.ColBoard;
import com.ssafy.mvc.service.BoardService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/board")
public class BoardController {
	
	BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	/**
	 * 카테고리별 게시글 전체목록 조회
	 *  (1, '운동/트레이닝'), (2, '재활/통증 관리'), (3, '영양/식단')
	 *  (4, '정신 건강/라이프스타일'), (5, '의학/질환 정보')
	 *  서버오류(500) 경우 : DB 접속실패,SQL 문법오류, 자바 내부예외
	 */ 
	@GetMapping("/category/{boardNum}")
	public ResponseEntity<List<ColBoard>> getMethodName1(@PathVariable("boardNum") int boardNum) {
		
		try {
			List<ColBoard> list = boardService.getCategoryBoard(boardNum);	// 1~5번 이외 sql 조회 시 exception 발생.
			if (list != null) {
				// 정상동작:200
				return ResponseEntity.status(HttpStatus.OK).body(list);
			} else {
				// 데이터가 없는경우 404 [오류x]
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			// 서버오류 500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();	
		}
	}
	
	
	/**
	 * 특정 (1개)의 게시물 가져오기, colboard_Id만을 이용하여 조회
	 * 서버오류(500) : DB 접속실패,SQL 문법오류, 자바 내부예외
	 */
	@GetMapping({"{colboardId}"})
	public ResponseEntity<ColBoard> getMethodName2(@PathVariable("colboardId") int colboardId) {
		try {
			ColBoard colBoard = boardService.getOneBoard(colboardId);
			if (colBoard != null) {	// 등록성공 200
				return ResponseEntity.status(HttpStatus.OK).body(colBoard);
			} else {	// 데이터가 없는경우 404	[오류x]
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {	// 서버오류 500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * 카테고리별 게시글 등록 
	 *  (1, '운동/트레이닝'), (2, '재활/통증 관리'), (3, '영양/식단')
	 *  (4, '정신 건강/라이프스타일'), (5, '의학/질환 정보')
	 */
	@PostMapping
	public ResponseEntity<String> postMethodName(@RequestBody ColBoard colBoard) {
		
		int result = boardService.intsertCategoryBoard(colBoard);
		if(result == 1) {	// 등록성공 201
			return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 등록 되었습니다.");
		} 	
		
		// 서버오류 500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록실패");
	}
	
	
	/**
	 * colboard_id(기본키)와 ColBoard객체를 이용한 게시글 정보 수정
	 */
	@PutMapping("{colboardId}")
	public ResponseEntity<String> postMethodName(@PathVariable("colboardId") int colboardId, @RequestBody ColBoard colBoard) {
		System.out.println(colBoard);
		
		colBoard.setColboardId(colboardId);
		
		int result = boardService.updateBoard(colBoard);
		if (result == 1) {
			return ResponseEntity.status(HttpStatus.OK).body("성공적으로 업데이트 되었습니다");
		} 
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류로 인해서 업데이트 불가");
	}
	

	@DeleteMapping("{colboardId}")
	public ResponseEntity<String> deleteMethod(@PathVariable("colboardId") int colboardId) {
		
		int result = boardService.deleteBoard(colboardId);
		if (result == 1) {
			return ResponseEntity.status(HttpStatus.OK).body("성공적으로 삭제 되었습니다");
		} 
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류로 인해서 삭제 불가");
	}

	// search 기능

	
}
