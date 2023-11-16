package com.youro.web.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.youro.web.pojo.Request.ProfilePictureRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.service.AmazonS3Service;

@RestController
@CrossOrigin(origins ="*")
@RequestMapping("/youro/api/v1/")
public class AmazonS3Controller {
	
	 @Autowired
	 AmazonS3Service s3Service;
	
	 @PostMapping("/uploadResults")
	 public List<byte[]> uploadResults(@RequestParam("files") MultipartFile[] files, @RequestParam("patientId") int patientId) {
		s3Service.uploadResults(files, patientId);
	   	return s3Service.getResults(patientId);
	 }
	    
	 @GetMapping("/getResults/{patientId}")
	 public List<byte[]> getResults(@PathVariable("patientId") int patientId) {
	    return s3Service.getResults(patientId);
	 }
	 
	 @DeleteMapping("/deleteResults")
	 public List<byte[]> deleteResults(@RequestParam("filename") String filename, @RequestParam("patientId") int patientId) {
		s3Service.deleteResults(filename, patientId);
	   	return s3Service.getResults(patientId);
	 }
	 
	 @PostMapping("/uploadDp")
	 public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("userId") int userId) throws IOException {
		 BasicResponse resp = s3Service.uploadImage(userId, file);
	     return ResponseEntity.status(HttpStatus.OK).body(resp);
	 }
	 
	 @GetMapping("/getDp/{id}")
	 public ResponseEntity<?> getImage(@PathVariable("id") int id) throws IOException{
	    byte[] image = s3Service.getImage(id);
	    if(image !=null) {
	    	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
	    }
	    else {
	    	return null;
	    }
	 }
	 
}
