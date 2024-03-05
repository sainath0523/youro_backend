package com.youro.web.service;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.youro.web.entity.Chat;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.Request.SaveChatRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.repository.MessageRepository;
import com.youro.web.utils.HelpUtils;

@Service
public class MessageService {
	private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public BasicResponse saveMessage(SaveChatRequest message) {
    	try {
            Chat chat = new Chat();
            chat.setSeen(false);
            chat.setFromId(HelpUtils.getUser(message.from));
            chat.setToId(HelpUtils.getUser(message.to));
            chat.setMessage(message.msg);
            chat.setDateTime(new Date());
            messageRepository.save(chat);
            return new BasicResponse("Chat Saved Successfully");
            
        }catch (Exception e)
        {
            throw new CustomException(e.getLocalizedMessage());
        }
        
    }
}
