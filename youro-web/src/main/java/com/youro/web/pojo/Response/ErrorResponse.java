package com.youro.web.pojo.Response;

public class ErrorResponse {
	String error;
	String errorDescription;
	
	public ErrorResponse(String error, String errorDescription) {
		this.error = error;
		this.errorDescription = errorDescription;
	}
	
	public ErrorResponse() {
	}

	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
