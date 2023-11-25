package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatHistoryResponse {

    public byte[] picture;
    public int count;
    public String message;
    public int uId;
    public String name;
}