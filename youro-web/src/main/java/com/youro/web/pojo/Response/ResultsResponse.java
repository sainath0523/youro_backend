package com.youro.web.pojo.Response;

public class ResultsResponse {
	String fileName;
	byte[] data;
	public ResultsResponse(String fileName, byte[] data) {
		super();
		this.fileName = fileName;
		this.data = data;
	}
	public ResultsResponse() {
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
}
