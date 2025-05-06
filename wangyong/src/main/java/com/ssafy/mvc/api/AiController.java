package com.ssafy.mvc.api;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AiController {
	
	private final OpenAiChatModel openAiChatModel;
	
	public AiController(OpenAiChatModel openAiChatModel) {
		this.openAiChatModel = openAiChatModel;
	}
	
	
	@GetMapping("/chatGPT")
	public ResponseEntity<String> chat(@RequestParam("message") String message){
		System.out.println(message);
		
		String result = openAiChatModel.call(message);
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}

