package com.ssafy.mvc.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.ssafy.mvc.model.dao.UserDao;
import com.ssafy.mvc.model.dto.LoginRequest;
import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.dto.UserDetail;
import com.ssafy.mvc.model.dto.UserFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

	@Value("${file.upload.directory}")
	private String uploadDir;

	// 싱글톤 의존성 주입
	private UserDao userDao;
	private FileService fileService;

	public UserServiceImpl(UserDao userDao, FileService fileService) {
		this.userDao = userDao;
		this.fileService = fileService;
	}

	@Override
	public int insertUser(User user) throws IOException {
		// User 정보 DB 저장 먼저
		int result = userDao.insertUser(user);
		
		// file 저장 (User 정보가 저장된 후에)
		if (result == 1 && user.getAttach() != null) {
			UserFile userFile = new UserFile();
			userFile.setUserId(user.getUserId());
			userFile.setOriginalName(user.getAttach().getOriginalFilename());
			userFile.setUploadName(UUID.randomUUID().toString() + "_" + user.getAttach().getOriginalFilename());
			userFile.setFileSize(user.getAttach().getSize());
			//User 이미지 DB 저장
			fileService.uploadProfileImage(userFile);
			
			// 업로드 디렉토리 생성
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			Path filePath = uploadPath.resolve(userFile.getUploadName()).normalize();
            Files.copy(user.getAttach().getInputStream(), filePath);
		}
		return result;
	}

	@Override
	public int updateUser(User user, String userId) throws IOException {
		User originUser = userDao.findUserByIdUser(userId);
		originUser.setPassword(user.getPassword());
		originUser.setUserName(user.getUserName());
		originUser.setUserRole(user.getUserRole());
		originUser.setUserDetail(user.getUserDetail());

		// user 테이블 업데이트
		int result1 = userDao.updateUser(originUser);

		// user_detail 테이블 업데이트
		UserDetail userDetail = originUser.getUserDetail();
		userDetail.setUserId(userId);  // userId 설정
		int result2 = userDao.updateUserDetail(userDetail);


		// file 저장 (User 정보가 저장된 후에)
		if (result1 == 1 && result2 == 1 && user.getAttach() != null) {
			//이미지 파일 지운 뒤에 다시 업로드
			fileService.deleteProfileImage(userId);
			UserFile userFile = new UserFile();
			userFile.setUserId(user.getUserId());
			userFile.setOriginalName(user.getAttach().getOriginalFilename());
			userFile.setUploadName(UUID.randomUUID().toString() + "_" + user.getAttach().getOriginalFilename());
			userFile.setFileSize(user.getAttach().getSize());
			//User 이미지 DB 저장
			fileService.uploadProfileImage(userFile);

			// 업로드 디렉토리 생성
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			Path filePath = uploadPath.resolve(userFile.getUploadName()).normalize();
			Files.copy(user.getAttach().getInputStream(), filePath);
		}
		return (result1 == 1 && result2 == 1) ? 1 : 0;
	}

	@Override
	public LoginRequest login(LoginRequest loginRequest) {
		// service에서 로그인로직 처리
		LoginRequest user = userDao.findUserByIdLogin(loginRequest.getUserId());

		if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
			throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다");
		} else {
			return user;
		}
	}

	@Override
	public User getUserInfo(String userId) {
		return userDao.findUserByIdUser(userId);
	}

	@Override
	public int updateUserDetail(UserDetail userDetail) {
		return userDao.updateUserDetail(userDetail);
	}


	@Override
	public boolean confirmId(String id) {
		// 1. id 중복성 검사
		int result = userDao.confirmId(id);

		if (result == 1) {	// 중복
			return false;
		}
		return true;		// 비중복
	}

	@Override
	public boolean confirmName(String name) {
		// Name중복성 검사
		int result = userDao.confirmName(name);
		if (result == 1) {	// 중복
			return false;
		}
		return true;		// 비중복
	}

}
