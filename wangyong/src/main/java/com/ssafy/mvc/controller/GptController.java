package com.ssafy.mvc.controller;

import com.ssafy.mvc.model.dto.ColBoard;
import com.ssafy.mvc.service.BoardService;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class GptController {

    private final OpenAiChatModel openAiChatModel;
    private final BoardService boardService;

    public GptController(OpenAiChatModel openAiChatModel, BoardService boardService) {
        this.openAiChatModel = openAiChatModel;
        this.boardService = boardService;
    }

    @PostMapping("/chatGPT")
    public ResponseEntity<Map<String, Object>> chat(@RequestBody String message) {
        String result = openAiChatModel.call(message);
        List<ColBoard> recommended = boardService.recommendByEmbedding(message, 3);

        Map<String, Object> response = new HashMap<>();
        response.put("answer", result);
        response.put("recommendedBoards", recommended);

        return ResponseEntity.ok(response);
    }
}