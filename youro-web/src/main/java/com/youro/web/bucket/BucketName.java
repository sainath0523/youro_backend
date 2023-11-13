package com.youro.web.bucket;

public enum BucketName {
	PROFILE_IMAGE("careplan-results");
	
	private final String bucketName;

	BucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getBucketName() {
		return bucketName;
	}
	
}
