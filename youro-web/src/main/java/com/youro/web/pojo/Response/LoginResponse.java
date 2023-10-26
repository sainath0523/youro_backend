package com.youro.web.pojo.Response;

public class LoginResponse {
    private String token;
	private int userId;
	private String uType;
    
    public LoginResponse(String token, int userId, String uType, long expiresIn) {
		super();
		this.userId = userId;
		this.token = token;
		this.uType = uType;
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

	// Getters and setters...
}