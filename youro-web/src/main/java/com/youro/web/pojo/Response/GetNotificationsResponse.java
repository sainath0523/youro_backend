package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetNotificationsResponse {
    public int notId;
    public String date;
    public int uId;
    public String message;
}