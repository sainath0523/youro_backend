package com.youro.web.pojo.Response;

public class LoginResponse {
    private String token;
	private int userId;
	private String uType;
	private String fullName;
	private String email;
	private boolean verificationStatus;

	public LoginResponse(String token, int userId, String uType, String fullName, String email,
			boolean verificationStatus) {
		super();
		this.token = token;
		this.userId = userId;
		this.uType = uType;
		this.fullName = fullName;
		this.email = email;
		this.verificationStatus = verificationStatus;
	}

	public String getToken() {
        return token;
    }

	public void setToken(String token) {
		this.token = token;
	}


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getuType() {
		return uType;
	}

	public void setuType(String uType) {
		this.uType = uType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public boolean isVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(boolean verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}