package com.ssafy.mvc.service;

import java.util.List;

import com.ssafy.mvc.model.dto.Review;

public interface ReviewService {
     int insertReview(Review review);
     List<Review> selectAllReviews(int colboardId);
    int removeReview(int reviewId);
    int updateReview(int reviewId, Review review);
}
