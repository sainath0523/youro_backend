package com.youro.web.pojo.Request;

import org.springframework.web.multipart.MultipartFile;

public class ProfilePictureRequest {
	
	int userId;
	MultipartFile imageFile;
	
	public ProfilePictureRequest(int userId, MultipartFile imageFile) {
		this.userId = userId;
		this.imageFile = imageFile;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public MultipartFile getImageFile() {
		return imageFile;
	}
	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}
}
