package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultsResponse {
	String fileName;
	byte[] data;
	public ResultsResponse(String fileName, byte[] data) {
		super();
		this.fileName = fileName;
		this.data = data;
	}
}