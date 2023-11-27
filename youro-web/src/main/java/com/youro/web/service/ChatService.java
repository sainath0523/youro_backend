package com.youro.web.service;

import com.youro.web.entity.Chat;
import com.youro.web.exception.CustomException;
import com.youro.web.mapper.ChatMapper;
import com.youro.web.pojo.Request.SaveChatRequest;
import com.youro.web.pojo.Request.UpdateChatRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Response.ChatHistoryResponse;
import com.youro.web.pojo.Response.ChatResponse;
import com.youro.web.repository.ChatRepository;
import com.youro.web.repository.UserRepository;
import com.youro.web.utils.HelpUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AmazonS3Service amazonS3Service;

    static SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)");


    @Transactional
    public BasicResponse updateLastSeen(UpdateChatRequest request)
    {
        try {
            Date date = inputFormat.parse(request.time);
            //TimeZone timeZone = inputFormat.getTimeZone();
            date = HelpUtils.addTime(date, 30);
            chatRepository.updateChat(request.fromId, request.toId, date);
        } catch (ParseException e) {
            throw new CustomException(e.getLocalizedMessage());
        }
        return new BasicResponse("Chat Updated Successfully");
    }
    public List<ChatResponse> getchat(Integer docId, Integer patId)
    {
        try {

            List<ChatResponse> response = new ArrayList<>();
            List<Chat> chat  = chatRepository.getChatsByUserId(docId, patId);
            for(Chat chat2 : chat)
            {
                ChatResponse resp = new ChatResponse();
                resp.fromId = chat2.getFromId().getUserId();
                resp.toId = chat2.getToId().getUserId();
                resp.message = chat2.message;
                resp.time = inputFormat.format(chat2.dateTime);
                response.add(resp);
            }
            return response;
        }catch (Exception e)
        {
            throw new CustomException(e.getLocalizedMessage());
        }
    }


    public List<ChatHistoryResponse> getChatHistory(int uId)
    {
        List<Object[]> getChatHistory = chatRepository.getChatHistory(uId);
        return ChatMapper.chatEntitytoResponse(getChatHistory, uId, chatRepository, userRepository, amazonS3Service);
    }

    public BasicResponse saveChat(SaveChatRequest request)
    {
        try {
            Chat chat = new Chat();
            chat.setSeen(false);
            chat.setFromId(HelpUtils.getUser(request.from));
            chat.setToId(HelpUtils.getUser(request.to));
            chat.setMessage(request.msg);
            chat.setDateTime(new Date());
            chatRepository.save(chat);
            return new BasicResponse("Chat Saved Successfully");
        }catch (Exception e)
        {
            throw new CustomException(e.getLocalizedMessage());
        }
    }

}