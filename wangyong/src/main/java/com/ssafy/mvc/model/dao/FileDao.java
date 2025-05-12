package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.BoardFile;

public interface FileDao {
	List<BoardFile> getFiles(int colboardId);	// 해당 게시판의 파일 가져오기
	
	// 기본 => 게시물삭제 cascade 삭제
	// 업데이트 파일만 삭제하기 위한 삭제가 필요
	int deleteFile(int colboardId);

}
