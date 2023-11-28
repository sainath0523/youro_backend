package com.youro.web.pojo.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatUser {
	int userId;
	String fullName;
	String userEmail;
	byte[] image;
}
