package com.ssafy.mvc.service;

import java.util.List;

import com.ssafy.mvc.model.dto.Review;

public interface ReviewService {
public int insertReview(Review review);
public List<Review> selectAllReviews(int colboardId);
	

}
