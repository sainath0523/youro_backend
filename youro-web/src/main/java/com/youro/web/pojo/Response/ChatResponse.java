package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatResponse {

    public String message;
    public int fromId;
    public int toId;
    public String time;
}