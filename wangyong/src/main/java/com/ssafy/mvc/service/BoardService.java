package com.ssafy.mvc.service;

import java.util.List;

import com.ssafy.mvc.model.dto.BoardFile;
import com.ssafy.mvc.model.dto.BoardLike;
import com.ssafy.mvc.model.dto.ColBoard;
import com.ssafy.mvc.model.dto.SearchCondition;
import com.ssafy.mvc.model.dto.User;

public interface BoardService {
	//
	List<ColBoard> getSearchBoard(SearchCondition condition);
	
	List<ColBoard> getCategoryBoard(int categoryNum);
	
	int insertCategoryBoard(ColBoard colBoard);	// flag = 0 (일반등록)  1(업데이트)

	ColBoard getOneBoard(int colboardId);
	ColBoard getOneBoardWithoutCnt(int colboardId);
	
	int updateBoard(ColBoard colBoard);
	
	int deleteBoard(int colboardId);
	
	List<ColBoard> getBoardlistByUser(String userId);
	
	// 업데이트 전용
	boolean forupdate(ColBoard colBoard);
	
	// 업로드 가장 최근 3개 가져오기
	List<ColBoard> getRecentBoard();
    List<ColBoard> getPopularBoard();

    int clickBoardLike(BoardLike boardLike);
    
    int getLikeCount (int colboardId);
    
	List<ColBoard> getUserPopularBoard(String userId);
	
	List<List<Integer>> getBestWriterBoards();
	
	List<ColBoard> getTop3(int colboardId);

	//유사게시글 추천메서드
	List<ColBoard> recommendByEmbedding(String question, int topN);
	
	
	
	}
