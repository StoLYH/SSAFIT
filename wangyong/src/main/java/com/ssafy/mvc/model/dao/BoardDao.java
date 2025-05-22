package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.BoardFile;
import com.ssafy.mvc.model.dto.BoardLike;
import com.ssafy.mvc.model.dto.ColBoard;
import com.ssafy.mvc.model.dto.SearchCondition;


public interface BoardDao {
	List<ColBoard> search(SearchCondition condition);
	
	// 카테고리별 전체조회
	List<ColBoard> getCategory(int categoryNum);
	
	int insertBoard(ColBoard colBoard); 
	
	ColBoard getBoard(int colboardId);
	
	int update(ColBoard colBoard);
	
	int delete(int colboardId);
	
	List<ColBoard> getBoardByUser(String userId);
	
	// 조회수 증가하기
	int upBoardCnt(int colboardId);
	
	// 파일등록
	int insertBoardFile(BoardFile boardFile);
	
	List<ColBoard> getRecentBoardList();


    List<ColBoard> getPopularBoardList();


    int confirmClick(BoardLike boardLike);
    int deleteClick(BoardLike boardLike);
    int insertClick(BoardLike boardLike);
    
    int getLike(int colboardId);

	List<ColBoard> getUserPopularBoardList(String userId);

}
