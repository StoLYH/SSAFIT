package com.ssafy.mvc.model.dto;

public class ColBoard {
	
    private int colboardId;        // 기본키
    private String userId;         
    private int category;          
    private String title;          
    private String content;        
    private int viewCnt;           
    private String createdAt;
   
	public ColBoard() {}

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



	public int getCategory() {
		return category;
	}



	public void setCategory(int category) {
		this.category = category;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public int getViewCnt() {
		return viewCnt;
	}



	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}



	public String getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}



	@Override
	public String toString() {
		return "ColBoard [colboardId=" + colboardId + ", userId=" + userId + ", category=" + category + ", title="
				+ title + ", content=" + content + ", viewCnt=" + viewCnt + ", createdAt=" + createdAt + "]";
	}      
}
