package com.ssafy.mvc.model.dto;

public class UserFile {
	private int fileId;				// 파일 기본키
    private String userId; 		// 사용자 ID (외래키)
    private String originalName;	// 원본 파일이름
    private String uploadName;		// 서버저장 파일이름
    private long fileSize;			// 파일 크기

    public UserFile() {}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "UserFile [fileId=" + fileId + ", userId=" + userId + ", originalName=" + originalName
				+ ", uploadName=" + uploadName + ", fileSize=" + fileSize + "]";
	}
	
}
