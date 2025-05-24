package com.ssafy.mvc.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class GptController {
	
	private final OpenAiChatModel openAiChatModel;
	
	public GptController(OpenAiChatModel openAiChatModel) {
		this.openAiChatModel = openAiChatModel;
	}
	
	
	@PostMapping("/chatGPT")
	public ResponseEntity<String> chat(@RequestBody String message) {
		
		String result = openAiChatModel.call(message);
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}