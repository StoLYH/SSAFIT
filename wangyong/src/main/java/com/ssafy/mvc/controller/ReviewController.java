package com.ssafy.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.model.dto.Review;
import com.ssafy.mvc.service.ReviewService;

@RestController
@RequestMapping("/review")
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
	
	
	

}
