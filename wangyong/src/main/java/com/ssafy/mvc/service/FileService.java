package com.ssafy.mvc.service;

import java.util.List;

import com.ssafy.mvc.model.dto.BoardFile;
import com.ssafy.mvc.model.dto.UserFile;

public interface FileService {
	
	List<BoardFile> getBoardFiles(int colboardId);
	
	// 프로필 이미지 관련 메서드 추가
	UserFile getProfileImage(String userId);
	int uploadProfileImage(UserFile userFile);
	int deleteProfileImage(String userId);
}
