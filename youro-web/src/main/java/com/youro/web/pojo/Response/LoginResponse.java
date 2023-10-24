package com.youro.web.pojo.Response;

public class LoginResponse {
    private String token;
    
    public LoginResponse(String token, long expiresIn) {
		super();
		this.token = token;
	}

    public String getToken() {
        return token;
    }

	public void setToken(String token) {
		this.token = token;
	}

	// Getters and setters...
}