package com.ssafy.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.BoardDao;
import com.ssafy.mvc.model.dto.ColBoard;

@Service
public class BoardServiceImpl implements BoardService{

	BoardDao boardDao;
	
	@Autowired
	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}


	@Override
	public List<ColBoard> getCategoryBoard(int categoryNum) {
		return boardDao.getCategory(categoryNum);
	}


	@Override
	public int intsertCategoryBoard(ColBoard colBoard) {
		return boardDao.insertBoard(colBoard);
	}


	@Override
	public ColBoard getOneBoard(int colboardId) {
		// 조회를 하였기 때문에 조회수 1 증가해야 한다.
		boardDao.upBoardCnt(colboardId);
		
		return boardDao.getBoard(colboardId);
	}


	@Override
	public int updateBoard(ColBoard colBoard) {
		return boardDao.update(colBoard);
	}


	@Override
	public int deleteBoard(int colboardId) {
		return boardDao.delete(colboardId);
	}


	@Override
	public List<ColBoard> getBoardlistByUser(String userId) {
		return boardDao.getBoardByUser(userId);
	}

	
}
