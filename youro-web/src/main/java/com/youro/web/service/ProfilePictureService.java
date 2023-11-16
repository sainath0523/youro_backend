package com.youro.web.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.youro.web.entity.User;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.Request.ProfilePictureRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.repository.UserRepository;

@Service
public class ProfilePictureService {
	
	@Autowired
	UserRepository userRepository;
	

//	public BasicResponse uploadImage(ProfilePictureRequest ppRequest) throws IOException {
//		
//		Optional<User> user = userRepository.findById(ppRequest.getUserId());
//		if(!user.isEmpty()) {
//			MultipartFile file = ppRequest.getImageFile();
//			user.get().setProfilePicture(compressImage(file.getBytes()));
//			userRepository.save(user.get());
//			return new BasicResponse("uploaded successfully");	
//		}
//		else {
//			throw new CustomException("No user found with id: " + ppRequest.getUserId());	
//		}
//	}
	
//	public byte[] getImage(int id) {
//		Optional<User> user = userRepository.findById(id);
//		if(!user.isEmpty()) {
//			byte[] image = decompressImage(user.get().getProfilePicture());
//	        return image;
//		}
//		else {
//			throw new CustomException("No user found with id: " + id);
//		}
//	}
	
//	public BasicResponse removeImage(int id) {
//		Optional<User> user = userRepository.findById(id);
//		if(!user.isEmpty()) {
//			user.get().setProfilePicture(null);
//			userRepository.save(user.get());
//			return new BasicResponse("Removed successfully");	
//		}
//		else {
//			throw new CustomException("No user found with id: " + id);	
//		}
//	}

	public static byte[] compressImage(byte[] data) {

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception e) {
        }
        System.out.println("length of image: " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
    	if(data != null) {
	        Inflater inflater = new Inflater();
	        inflater.setInput(data);
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
	        byte[] tmp = new byte[4*1024];
	        try {
	            while (!inflater.finished()) {
	                int count = inflater.inflate(tmp);
	                outputStream.write(tmp, 0, count);
	            }
	            outputStream.close();
	        } catch (Exception exception) {
	        }
	        return outputStream.toByteArray();
    	}
    	return null;
    }

}
