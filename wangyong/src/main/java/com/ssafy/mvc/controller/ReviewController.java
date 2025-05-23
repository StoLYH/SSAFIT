package com.ssafy.mvc.controller;

import java.util.ArrayList;

import java.util.List;

import com.ssafy.mvc.model.dto.ColBoard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.mvc.model.dto.Review;
import com.ssafy.mvc.service.ReviewService;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/review")
public class ReviewController {
	
	private final ReviewService reviewService;
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
		
	}
	

	//리뷰 등록
	@PostMapping
	public ResponseEntity<String> registReview(@RequestBody Review review ){ 
		reviewService.insertReview(review);
		return ResponseEntity.ok("성공!");
	}
	//colboardId 받아서 해당 리뷰 보여줌 
	@GetMapping("{colboardId}")
	public ResponseEntity<List<Review>>getAllReviews(@PathVariable int colboardId){
		System.out.println(colboardId);
		List<Review>list = reviewService.selectAllReviews(colboardId);
		if(!list.isEmpty()) {
			return ResponseEntity.ok(list);	
		}
		return ResponseEntity.noContent().build();
		
	}


	//리뷰 삭제
	@DeleteMapping("{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable int reviewId){
		reviewService.removeReview(reviewId);
		return ResponseEntity.ok("성공");
	}

	//리뷰수정
	@PutMapping("{reviewId}")
	public ResponseEntity<String> updateReview(@PathVariable int reviewId, @RequestBody Review review) {

		int result = reviewService.updateReview(reviewId,review);
		if (result == 1) {
			return ResponseEntity.status(HttpStatus.OK).body("성공적으로 업데이트 되었습니다");
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류로 인해서 업데이트 불가");
	}

	
	
	
	

}
