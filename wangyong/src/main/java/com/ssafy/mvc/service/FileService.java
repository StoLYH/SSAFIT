package com.ssafy.mvc.service;

import java.util.List;

import com.ssafy.mvc.model.dto.BoardFile;

public interface FileService {
	
	List<BoardFile> getBoardFiles(int colboardId);

}
