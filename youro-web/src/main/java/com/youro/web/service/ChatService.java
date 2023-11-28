package com.youro.web.service;

import com.youro.web.entity.Appointments;
import com.youro.web.entity.Chat;
import com.youro.web.entity.User;
import com.youro.web.entity.UserType;
import com.youro.web.exception.CustomException;
import com.youro.web.mapper.ChatMapper;
import com.youro.web.pojo.Request.SaveChatRequest;
import com.youro.web.pojo.Request.UpdateChatRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Response.ChatHistoryResponse;
import com.youro.web.pojo.Response.ChatResponse;
import com.youro.web.pojo.Response.ChatUser;
import com.youro.web.repository.AppointmentsRepository;
import com.youro.web.repository.ChatRepository;
import com.youro.web.repository.UserRepository;
import com.youro.web.utils.HelpUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AmazonS3Service amazonS3Service;
    
    @Autowired
    AppointmentsRepository appointmentsRepository;

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

	public List<ChatUser> getChatUsers(Integer userId) throws IOException {
        List<ChatUser> chatUsers = new ArrayList<>();

        List<Appointments> res = new ArrayList<>();
        res.addAll(appointmentsRepository.findByUId(userId));
        
        Optional<User> user = userRepository.findById(userId);
        List<Integer> userList = new ArrayList<>();
        
        if(user.get().userType == UserType.PATIENT) {      
        	for(Appointments appt : res) {
        		int docId = appt.getDoctorId().userId;
                User doc = userRepository.findById(docId).get();
                String fullName = doc.firstName + " " + doc.lastName;
                String email = doc.email;
                byte[] image = amazonS3Service.getImage(docId);

               if(!userList.contains(docId)) {
                    ChatUser chatUser = new ChatUser(docId, fullName, email, image);
                    chatUsers.add(chatUser);
                    userList.add(docId);
               } 
        	}
		}
        
        else if(user.get().userType == UserType.PROVIDER) {            
        	for(Appointments appt : res) {
        		int patId = appt.getPatientId().userId;
                User pat = userRepository.findById(patId).get();
                String fullName = pat.firstName + " " + pat.lastName;
                String email = pat.email;   
                byte[] image = amazonS3Service.getImage(patId);
                if(!userList.contains(patId)) {
                    ChatUser chatUser = new ChatUser(patId, fullName, email, image);
                    chatUsers.add(chatUser);
                    userList.add(patId);
               }           
            }    
		}
		return chatUsers;
	}


}