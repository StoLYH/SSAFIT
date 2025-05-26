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

    public double cosineSimilarity(List<Float> v1, List<Float> v2) {
        if (v1 == null || v2 == null) return 0.0;
        double dot = 0, norm1 = 0, norm2 = 0;
        for (int i = 0; i < v1.size(); i++) {
            dot += v1.get(i) * v2.get(i);
            norm1 += v1.get(i) * v1.get(i);
            norm2 += v2.get(i) * v2.get(i);
        }
        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
