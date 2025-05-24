package com.ssafy.mvc.model.dto;

public class Review {
	private int reviewId;
	private int colboardId;
	private String userId;
	private String content;
	private String createdAt;
	
	
	//생성자
	public Review(int reviewId, int colboardId, String userId, String content, String createdAt) {	
		this.reviewId = reviewId;
		this.colboardId = colboardId;
		this.userId = userId;
		this.content = content;
		this.createdAt = createdAt;
	}
	
	//기본생성자
	
	public Review() {
	}



	//getter setter
	
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getColboardId() {
		return colboardId;
	}
	public void setColboardId(int colboardId) {
		this.colboardId = colboardId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	


}
