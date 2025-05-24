package com.ssafy.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.FileDao;
import com.ssafy.mvc.model.dto.BoardFile;
import com.ssafy.mvc.model.dto.UserFile;

@Service
public class FileServiceImpl implements FileService{

	FileDao fileDao;
	
	@Autowired
	public FileServiceImpl(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	@Override
	public List<BoardFile> getBoardFiles(int colboardId) {
		return fileDao.getFiles(colboardId);
	}

	@Override
	public UserFile getProfileImage(String userId) {
		return fileDao.getProfileImage(userId);
	}

	@Override
	public int uploadProfileImage(UserFile userFile) {
		return fileDao.insertProfileImage(userFile);
	}

	@Override
	public int deleteProfileImage(String userId) {
		return fileDao.deleteProfileImage(userId);
	}

	@Override
	public UserFile getUserFiles(String userId) {
		return fileDao.getUserFile(userId);
	}
}
