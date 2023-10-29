package com.youro.web.pojo.Response;

public class LoginResponse {
    private String token;
	private int userId;
	private String uType;
	private String fullName;
    
    public LoginResponse(String token, int userId, String uType, String fullName) {
		super();
		this.userId = userId;
		this.token = token;
		this.uType = uType;
		this.fullName = fullName;
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

}