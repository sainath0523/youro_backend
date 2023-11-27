package com.youro.web.controller;

import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Response.GetNotificationsResponse;
import com.youro.web.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/youro/api/v1/")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/getNotifications/{uId}")
    public List<GetNotificationsResponse> getNotifications(@PathVariable(name = "uId") Integer uId)
    {
         return notificationService.getNotifications(uId);
    }

    @DeleteMapping("/deleteNotifications/{notId}")
    public BasicResponse deleteNotification(@PathVariable(name = "notId") Integer notId)
    {
        return notificationService.deleteNotifications(notId);
    }
    
    @DeleteMapping("/deleteAllNotifications/{userId}")
    public BasicResponse deleteAllNotification(@PathVariable(name = "userId") Integer userId)
    {
        return notificationService.deleteAllNotifications(userId);
    }
}