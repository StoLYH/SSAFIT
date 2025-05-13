package com.ssafy.mvc.service;

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

}
