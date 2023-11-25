package com.youro.web.mapper;

import com.youro.web.entity.Notification;
import com.youro.web.pojo.Response.GetNotificationsResponse;

import java.util.ArrayList;
import java.util.List;

public class NotificationMapper {

    public static List<GetNotificationsResponse> EntityToResponseMapper(List<Notification> notificationList)
    {
        List<GetNotificationsResponse> response = new ArrayList<>();

        for(Notification notification : notificationList)
        {
            GetNotificationsResponse res = new GetNotificationsResponse();
            res.setMessage(notification.getMessage());
            res.setUId(notification.getUserId().getUserId());
            res.setNotId(notification.getNotId());
            response.add(res);
        }
        return response;
    }
}