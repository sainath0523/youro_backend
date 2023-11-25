package com.youro.web.pojo.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveChatRequest {

    public String msg;
    public int from;
    public int to;
}