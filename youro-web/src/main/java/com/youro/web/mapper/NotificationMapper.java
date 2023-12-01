package com.youro.web.mapper;

import com.youro.web.entity.Notification;
import com.youro.web.pojo.Response.GetNotificationsResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class NotificationMapper {

    static SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
    static SimpleDateFormat outFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    public static List<GetNotificationsResponse> EntityToResponseMapper(List<Notification> notificationList, String timeZone)
    {
        List<GetNotificationsResponse> response = new ArrayList<>();

        for(Notification notification : notificationList)
        {
            GetNotificationsResponse res = new GetNotificationsResponse();
            String message = notification.getMessage();
            if(message.contains("&&")) {
                String[] mes = message.split("&&");
                    String time = mes[1];
                try {

                    TimeZone targetTimeZone = TimeZone.getTimeZone(timeZone);  // Example: Eastern Time
                    outFormat.setTimeZone(targetTimeZone);

                    Date convertedTime = inputFormat.parse(time);
                    String resultStr = outFormat.format(convertedTime);

                    message = message.replace("&&" + mes[1] + "&&", resultStr);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            res.setMessage(message);
            res.setDate(outFormat.format(notification.getDateTime()));
            res.setUId(notification.getUserId().getUserId());
            res.setNotId(notification.getNotId());
            response.add(res);
        }
        return response;
    }
}