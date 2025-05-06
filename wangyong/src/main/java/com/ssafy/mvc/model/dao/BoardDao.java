package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.ColBoard;


public interface BoardDao {
	
	// 카테고리별 전체조회
	List<ColBoard> getCategory(int categoryNum);
	
	int insertBoard(ColBoard colBoard); 
	
	ColBoard getBoard(int colboardId);
	
	int update(ColBoard colBoard);
	
	int delete(int colboardId);
}
