package com.ssafy.mvc.model.dto;

public class SearchCondition {
	private String key;			// user_id(작가), title, content
	private String word;		// 검색어
	private String orderBy;		// view_cnt(조회수), created_at(시간)
	private String orderByDir;	// ASC,DESC
	
	public SearchCondition() {
	}
	
	
	
	public SearchCondition(String key, String word, String orderBy, String orderByDir) {
		this.key = key;
		this.word = word;
		this.orderBy = orderBy;
		this.orderByDir = orderByDir;
	}

	private String getKey() {
		return key;
	}
	private void setKey(String key) {
		this.key = key;
	}
	private String getWord() {
		return word;
	}
	private void setWord(String word) {
		this.word = word;
	}
	private String getOrderBy() {
		return orderBy;
	}
	private void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	private String getOrderByDir() {
		return orderByDir;
	}
	private void setOrderByDir(String orderByDir) {
		this.orderByDir = orderByDir;
	}
	
	
	@Override
	public String toString() {
		return "SearchCondition [key=" + key + ", word=" + word + ", orderBy=" + orderBy + ", orderByDir=" + orderByDir
				+ "]";
	}
}
