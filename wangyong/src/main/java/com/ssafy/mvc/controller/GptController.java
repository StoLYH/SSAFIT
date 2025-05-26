package com.ssafy.mvc.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class GptController {
	
	private final OpenAiChatModel openAiChatModel;
	private static final String SYSTEM_ROLE = "너는 내가 개발한 헬스트레이너, 의사,물리치료사, 영양사등 건강 전문가들이 지식을 공유하고 소통하는 지식 기반 커뮤니티 FITLOG의 고민상담, 전문지식을 알려주는 assistant야";
	
	public GptController(OpenAiChatModel openAiChatModel) {
		this.openAiChatModel = openAiChatModel;
	}
	
	@PostMapping("/chatGPT")
	public ResponseEntity<String> chat(@RequestBody String message) {
		// System 역할과 사용자 메시지를 포함한 Prompt 생성
		Prompt prompt = new Prompt(
			new SystemMessage(SYSTEM_ROLE),
			new UserMessage(message)
		);
		
		String result = openAiChatModel.call(prompt).getResult().getOutput().getText();
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}