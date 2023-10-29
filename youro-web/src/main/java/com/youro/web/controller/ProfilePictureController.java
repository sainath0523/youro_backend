package com.youro.web.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youro.web.entity.User;
import com.youro.web.pojo.Request.ProfilePictureRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.service.ProfilePictureService;

@RestController
@RequestMapping("/youro/api/v1/")
public class ProfilePictureController {
	
	@Autowired
	ProfilePictureService ppService;
	
	@PostMapping("/uploadDp")
    public ResponseEntity<?> uploadImage(@ModelAttribute ProfilePictureRequest ppRequest) throws IOException {
		
		BasicResponse resp = ppService.uploadImage(ppRequest);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
        
    }

    @GetMapping("/getDp/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") int id){

    	byte[] image = ppService.getImage(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);

    }
    
    @DeleteMapping("/removeDp/{id}")
    public ResponseEntity<?> removeImage(@PathVariable("id") int id){

    	BasicResponse resp = ppService.removeImage(id);
        return ResponseEntity.status(HttpStatus.OK).body(resp);

    }

}
