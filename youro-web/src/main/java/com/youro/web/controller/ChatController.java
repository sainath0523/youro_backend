package com.youro.web.controller;

import com.youro.web.pojo.Request.SaveChatRequest;
import com.youro.web.pojo.Request.UpdateChatRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Response.ChatHistoryResponse;
import com.youro.web.pojo.Response.ChatResponse;
import com.youro.web.pojo.Response.ChatUser;
import com.youro.web.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/youro/api/v1/")
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping("/saveChat")
    public BasicResponse saveChat(@RequestBody @Valid SaveChatRequest request)
    {
        return chatService.saveChat(request);
    }

    @PutMapping("/updateChat")
    public BasicResponse updateChat(@RequestBody @Valid UpdateChatRequest request)
    {
        return chatService.updateLastSeen(request);
    }

    @GetMapping("/getChat/{docId}/{patId}")
    public List<ChatResponse> getChat(@PathVariable(name = "docId") Integer docId, @PathVariable(name = "patId") Integer patId, @RequestParam(required = false, name="timeZone") String timeZone)
    {
        return chatService.getchat(docId, patId, timeZone);
    }

    @GetMapping("/getChatHistory/{uId}")
    public List<ChatHistoryResponse> getChatHistory(@PathVariable(name = "uId") Integer uId)
    {
        return chatService.getChatHistory(uId);
    }
    
    @GetMapping("/getChatUsers/{uId}")
    public List<ChatUser> getChatUsers(@PathVariable(name = "uId") Integer uId) throws IOException
    {
        return chatService.getChatUsers(uId);
    }

}