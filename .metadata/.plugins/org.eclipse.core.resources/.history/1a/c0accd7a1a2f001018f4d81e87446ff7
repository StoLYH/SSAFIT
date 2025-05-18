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

import com.ssafy.mvc.model.dto.BoardFile;
import com.ssafy.mvc.service.FileService;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 파일의 MIME 타입 추출
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

	/**
	 * 게시물 기본키를 이용해서 파일정보 조회
	 * 
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
