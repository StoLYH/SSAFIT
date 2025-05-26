// EmbeddingDTO.java
package com.ssafy.mvc.model.dto;

import java.util.List;

public class Embedding {
    private int colboardId;
    private List<Float> embedding;
    
    public Embedding() {}
    
    public Embedding(int colboardId, List<Float> embedding) {
        this.colboardId = colboardId;
        this.embedding = embedding;
    }
    
    // Getters and Setters
    public int getColboardId() {
        return colboardId;
    }
    
    public void setColboardId(int colboardId) {
        this.colboardId = colboardId;
    }
    
    public List<Float> getEmbedding() {
        return embedding;
    }
    
    public void setEmbedding(List<Float> embedding) {
        this.embedding = embedding;
    }
}