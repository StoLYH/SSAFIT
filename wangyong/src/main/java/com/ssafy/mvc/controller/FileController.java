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

	// 서버에 저장된 파일을 준다.		
	@GetMapping("/sendImg/{fileName}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {

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

	@GetMapping("user/{userId}")
	public ResponseEntity<UserFile> getMethod5(@PathVariable("userId") String userId) {
		UserFile userFile = fileService.getUserFiles(userId);
		if (userFile != null) {
			return ResponseEntity.status(HttpStatus.OK).body(userFile);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}


	
	 /**
	  * 파일 다운로드
	  * 이해 x => 가져다 쓴다.
	  */
	 @GetMapping("/download/{fileName}")
	 public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {

	     try {
	         // 실제 파일 경로 생성
	         Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
	         Resource resource = new UrlResource(filePath.toUri());

	         if (!resource.exists()) {
	             return ResponseEntity.notFound().build();
	         }

	         // 파일 확장자와 상관없이 다운로드 처리하도록 설정
	         String encodedFileName = java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

	         return ResponseEntity.ok()
	                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
	                 .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream") // 모든 바이너리 파일 지원
	                 .body(resource);

	     } catch (IOException e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	     }
	 }
}
