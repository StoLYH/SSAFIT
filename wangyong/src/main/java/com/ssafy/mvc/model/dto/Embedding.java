// EmbeddingDTO.java
package com.ssafy.mvc.model.dto;

import java.util.Arrays;

public class Embedding {
	private int colboardId;         // 게시글 ID (primary key)
    private byte[] embedding;       // 직렬화된 임베딩 데이터
    
    
    public Embedding() {}
    
	public int getColboardId() {
		return colboardId;
	}


	public void setColboardId(int colboardId) {
		this.colboardId = colboardId;
	}


	public byte[] getEmbedding() {
		return embedding;
	}


	public void setEmbedding(byte[] embedding) {
		this.embedding = embedding;
	}

	@Override
	public String toString() {
		return "Embedding [colboardId=" + colboardId + ", embedding=" + Arrays.toString(embedding) + "]";
	}
    
    
}