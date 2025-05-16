package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.Review;

public interface ReviewDao {
	int insertReview(Review review);
	List<Review> selectAllReviews(int colboardId);
    int removeReview(int reviewId);
	Review selectReview(int reviewId);
	int updateReview(Review originreview);
}
