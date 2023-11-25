package com.youro.web.mapper;

import com.youro.web.entity.User;
import com.youro.web.pojo.Response.ChatHistoryResponse;
import com.youro.web.repository.ChatRepository;
import com.youro.web.repository.UserRepository;
import com.youro.web.service.AmazonS3Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatMapper {

    public static List<ChatHistoryResponse> chatEntitytoResponse(List<Object[]> history, int uId, ChatRepository chatRepository, UserRepository userRepository, AmazonS3Service amazonS3Service)
    {
        List<ChatHistoryResponse> chatReponse = new ArrayList<>();
        for(Object[] obj : history)
        {
            ChatHistoryResponse resp = new ChatHistoryResponse();
            int userId = ((Long) obj[0]).intValue();
            User user = userRepository.findById(userId).get();
            resp.count = chatRepository.getUnSeenCount(user.getUserId(), uId);
            resp.message = (String) obj[1];
            resp.uId = user.getUserId();
            resp.name = user.firstName;
            try {
                resp.picture = amazonS3Service.getImage(user.getUserId());
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
            chatReponse.add(resp);
        }
    return chatReponse;
    }
}