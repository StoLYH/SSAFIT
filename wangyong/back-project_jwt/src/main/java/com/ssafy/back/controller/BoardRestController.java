package com.ssafy.back.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.back.model.dto.Board;
import com.ssafy.back.model.dto.SearchCondition;
import com.ssafy.back.model.service.BoardService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api-board")
@Tag(name = "BoardRestController", description = "게시판 CRUD")
@CrossOrigin("*")
public class BoardRestController {

	private final BoardService boardService;

//	@Autowired
	public BoardRestController(BoardService boardService) {
		this.boardService = boardService;
	}

	// 게시글 (검색) 조회
	@GetMapping("/board")
	public ResponseEntity<?> list(@ModelAttribute SearchCondition condition) {
		List<Board> list = boardService.searchBoard(condition); // 검색 조회
		if (list == null || list.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}

	// 게시글 상세 보기
	@GetMapping("/board/{id}")
	public ResponseEntity<Board> detail(@PathVariable int id) {
		Board board = boardService.readBoard(id);
		if (board != null)
			return new ResponseEntity<Board>(board, HttpStatus.OK);
		return new ResponseEntity<Board>(HttpStatus.NOT_FOUND);
	}

	// 게시글 등록 (JSON형태로 보낸다)
	@PostMapping("/board")
	public ResponseEntity<String> write(@RequestBody Board board) {
		if (boardService.writeBoard(board)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Board added successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add board");
	}

	// 게시글 수정 (JSON형태로 보낸다)
	@PutMapping("/board")
	public ResponseEntity<String> update(@RequestBody Board board) {
		if (boardService.modifyBoard(board))
			return ResponseEntity.ok("Board updated successfully");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Board");
	}

	// 게시글 삭제
	@DeleteMapping("/board/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		if (boardService.removeBoard(id))
			return ResponseEntity.status(HttpStatus.OK).body("Board deleted successfully");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete Board");
	}

}
