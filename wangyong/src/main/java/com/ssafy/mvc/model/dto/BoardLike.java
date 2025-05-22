package com.ssafy.mvc.model.dto;

public class BoardLike {
	
	private int likeId;
	private String userId;
	private int colboardId;
	
	public BoardLike() {}
	
	
	public int getLikeId() {
		return likeId;
	}
	public void setLikeId(int likeId) {
		this.likeId = likeId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getColboardId() {
		return colboardId;
	}
	public void setColboardId(int colboardId) {
		this.colboardId = colboardId;
	}


	@Override
	public String toString() {
		return "BoardLike [likeId=" + likeId + ", userId=" + userId + ", colboardId=" + colboardId + "]";
	}

}
