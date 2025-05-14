package com.ssafy.mvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.ReviewDao;
import com.ssafy.mvc.model.dto.Review;


@Service
public class ReviewServiceImpl implements ReviewService {
	
	
	//생성자 주입
	
	private final ReviewDao reviewDao;
	public ReviewServiceImpl(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}

	@Override
	public int insertReview(Review review) {
		return reviewDao.insertReview(review);
	}

	@Override
	public List<Review> selectAllReviews(int colboardId) {
		// TODO Auto-generated method stub
		return reviewDao.selectAllReviews(colboardId);
	}
	@Override
	public int removeReview(int reviewId) {
		return reviewDao.removeReview(reviewId);
	}

	@Override
	public int updateReview(int reviewId, Review review) {
		Review originreview = reviewDao.selectReview(reviewId);
		originreview.setContent(review.getContent());
		return reviewDao.updateReview(originreview);
	}

}
