package com.ssafy.mvc.service;

import java.util.List;

import com.ssafy.mvc.model.dto.ColBoard;

public interface BoardService {
	
	List<ColBoard> getCategoryBoard(int categoryNum);
	
	int intsertCategoryBoard(ColBoard colBoard);

	ColBoard getOneBoard(int colboardId);
	
	int updateBoard(ColBoard colBoard);
	
	int deleteBoard(int colboardId);
}
