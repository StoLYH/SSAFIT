package com.ssafy.mvc.service;

import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;

@Service
public class EmbeddingService {
    private final OpenAiEmbeddingModel embeddingModel;

    public EmbeddingService(OpenAiEmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public List<Float> getEmbedding(String text) {
        EmbeddingRequest request = new EmbeddingRequest(List.of(text), null);
        EmbeddingResponse response = embeddingModel.call(request);
        float[] vector = response.getResults().get(0).getOutput();
        List<Float> floatList = new java.util.ArrayList<>(vector.length);
        for (float v : vector) {
            floatList.add(v);
        }
        return floatList;
    }


}
