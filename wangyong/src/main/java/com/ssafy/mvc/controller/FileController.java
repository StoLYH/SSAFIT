package com.ssafy.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dto.BoardFile;
import com.ssafy.mvc.model.dto.UserFile;
import com.ssafy.mvc.service.FileService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/upload")
public class FileController {
	
	@Value("${file.upload.directory}")
	private String uploadDir;				// application.properties에 존재
	
	FileService fileService;
	
	@Autowired
	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	// 프로필 이미지 업로드
	@PostMapping("/profile")
	public ResponseEntity<UserFile> uploadProfileImage(
			@RequestParam("file") MultipartFile file,
			@RequestParam("userId") String userId) {
		try {
			// 파일 정보 생성
			UserFile userFile = new UserFile();
			userFile.setUserId(userId);
			userFile.setOriginalName(file.getOriginalFilename());
			
			// 파일 저장 이름 생성 (UUID 사용)
			String uploadName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			userFile.setUploadName(uploadName);
			userFile.setFileSize(file.getSize());
			
			// 파일 저장
			Path filePath = Paths.get(uploadDir).resolve(uploadName).normalize();
			Files.copy(file.getInputStream(), filePath);
			
			// DB에 파일 정보 저장
			int result = fileService.uploadProfileImage(userFile);
			
			if (result > 0) {
				return ResponseEntity.status(HttpStatus.CREATED).body(userFile);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 프로필 이미지 정보 조회
	@GetMapping("/profile/{userId}")
	public ResponseEntity<UserFile> getProfileImage(@PathVariable String userId) {
		UserFile userFile = fileService.getProfileImage(userId);
		if (userFile != null) {
			return ResponseEntity.status(HttpStatus.OK).body(userFile);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	// 프로필 이미지 파일 제공
	@GetMapping("/profile/image/{fileName}")
	public ResponseEntity<Resource> serveProfileImage(@PathVariable String fileName) {
		try {
			Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
			if (!resource.exists()) {
				return ResponseEntity.notFound().build();
			}
			
			String contentType = Files.probeContentType(filePath);
			if (contentType == null) {
				contentType = "application/octet-stream";
			}
			
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
					.body(resource);
		} catch (MalformedURLException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	// 서버에 저장된 파일을 준다.
	@GetMapping("/sendImg/{fileName}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
		System.out.println("요첨옴?");
		
		
		System.out.println(fileName);
        try {
            // 경로를 지정하고 리소스 객체로 변환
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());	// 파일

            if (!resource.exists()) {	// 파일 존재하지 않는경우
                return ResponseEntity.notFound().build();
            }

            // 파일의 타입 추출 (image/png, image/jpeg, application/pdf)
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";	// pdf와 같은 바이너리 파일
            }

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contentType).body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

	/**
	 * 게시물 기본키를 이용해서 파일정보 조회
	 * 클라이언트 쪽에서 파일을 원하는 경우 => 선제적으로 파일정보를 가져가서 src로 파일 요청을 하도록 구성
	 */
	 @GetMapping("{colboardId}")
	public ResponseEntity<List<BoardFile>> getMethod4(@PathVariable("colboardId") int colboardId) {
		 
		 List<BoardFile> list = fileService.getBoardFiles(colboardId);
		 if (list != null) {
			 return ResponseEntity.status(HttpStatus.OK).body(list);
		 } else {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		 }
	 }

	
	
}
