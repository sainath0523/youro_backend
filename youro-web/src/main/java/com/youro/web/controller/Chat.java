package com.youro.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youro.web.entity.Message;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.service.ChatService;


@RequestMapping("/youro/api/v1/")
@Controller
public class Chat {

	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
    ChatService chatService;
	
	@MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }
	
	@MessageMapping("/private-message")
    public BasicResponse recMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        
        return chatService.saveWebChat(message);
    }
	
	/*@MessageMapping("/chat")
	public String handle(String chatID) {
		System.out.println("This chat has been called....... "+chatID);
		return "Chat has received";
	}*/
	
}