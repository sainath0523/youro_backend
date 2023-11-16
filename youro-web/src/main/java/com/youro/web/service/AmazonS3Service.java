package com.youro.web.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.youro.web.bucket.BucketName;
import com.youro.web.entity.Results;
import com.youro.web.entity.User;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.repository.ResultsRepository;
import com.youro.web.repository.UserRepository;
import com.youro.web.utils.HelpUtils;

@Service
public class AmazonS3Service {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ResultsRepository resultsRepository;
	
	@Autowired
	AmazonS3 s3;
	
	public BasicResponse uploadResults(MultipartFile[] files, int patientId) {
		
		if(files.length == 0)
			throw new CustomException("No files to upload");
		try {
			String results_folder = "Results";
			String bucketName = String.format("%s", BucketName.PROFILE_IMAGE.getBucketName());
			String rootPath = String.format("%s/%s", Integer.toString(patientId), results_folder);
			this.savetoS3(bucketName, rootPath, files);
			this.saveResultsUrl(patientId, bucketName, rootPath);
			return new BasicResponse("Files uploaded to s3 successfully");
		}
		catch(IOException e) {
			throw new CustomException(e.getLocalizedMessage());
		}
		
	}

	public void savetoS3(String bucketName, String rootPath, MultipartFile[] files) throws IOException {
		try {
			for (MultipartFile mFile: files) {
				File file = convertFile(mFile);
				String filePath = String.format("%s/%s", rootPath, file.getName());
				s3.putObject(bucketName, filePath, file);
			}
			System.out.println("succesfully uploaded files");			
		}
		catch(AmazonServiceException e){
			System.out.println(e);
			throw new CustomException("Failed to upload files to S3");
		}
	}
	
	private void saveResultsUrl(int patientId, String bucketName, String rootPath) {
		String results_url = String.format("%s/%s", bucketName, rootPath);

		Optional<User> user = userRepository.findByUserId(patientId);
		if(user.isEmpty()) {
            throw new CustomException("No user found with id: " + patientId);
		}
		Optional<Results> results = resultsRepository.findByPatientId(user.get());
		if(results.isEmpty()) {
			Results res = new Results();
			res.setPatientId(HelpUtils.getUser(patientId));
			res.setResults_url(results_url);
            res.setLastUpdated(new Date());
			resultsRepository.save(res);		
		}
		else {
			Results res = results.get();
            res.results_url = results_url;
            res.setLastUpdated(new Date());
            resultsRepository.save(res);
		}
				
	}
	
	public static File convertFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	public List<byte[]> getResults(int patientId) {
		Optional<User> user = userRepository.findByUserId(patientId);
		if(user.isEmpty()) {
            throw new CustomException("No user found with id: " + patientId);
		}
		Optional<Results> results = resultsRepository.findByPatientId(user.get());
		if(results.isEmpty()) {
            throw new CustomException("No results for patient: " + patientId);
		}
		String results_url = results.get().getResults_url();	
		return getResultsFromS3(results_url);
	}

	private List<byte[]> getResultsFromS3(String results_url) {
		try {
			String[] parts = results_url.split("/");
			String bucketName = parts[0];
		    String rootPath = parts[1];
		    
	        List<byte[]> resultList = new ArrayList<>();
	        ObjectListing objectListing = s3.listObjects(bucketName, rootPath);
	        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
	        
	        for (S3ObjectSummary objectSummary : objectSummaries) {
	            S3Object obj = s3.getObject(bucketName, objectSummary.getKey());
	            S3ObjectInputStream inputStream = obj.getObjectContent();
	            byte[] data = IOUtils.toByteArray(inputStream);
                resultList.add(data);
	        }
	        return resultList;			
		}
		catch(AmazonServiceException | IOException e) {
			throw new CustomException("Failed to get files from S3");
		}
	}
	
	public void deleteResults(String filename, int patientId) {
		String results_folder = "Results";
		String bucketName = String.format("%s", BucketName.PROFILE_IMAGE.getBucketName());
		String filePath = String.format("%s/%s/%s", Integer.toString(patientId), results_folder, filename);
        s3.deleteObject(bucketName, filePath);		
        System.out.println("File deleted from s3 successfully: " + filename);
	}

	public BasicResponse uploadImage(int userId, MultipartFile mFile) throws IOException {
		Optional<User> temp = userRepository.findById(userId);
		if(!temp.isEmpty()) {
			String profile_pic_folder = "Profile-picture";
			String bucketName = String.format("%s", BucketName.PROFILE_IMAGE.getBucketName());
			String rootPath = String.format("%s/%s", Integer.toString(userId), profile_pic_folder);
	        
			// delete the previous profile picture if any
			s3.deleteObject(bucketName, rootPath);		

			File file = convertFile(mFile);
			String filePath = String.format("%s/%s", rootPath, file.getName());
			s3.putObject(bucketName, filePath, file);
			
			String profile_url = String.format("%s/%s", bucketName, rootPath);
			User user = temp.get();
			user.setProfileUrl(profile_url);
			userRepository.save(user);
			
			return new BasicResponse("Uploaded profile picture to s3 successfully");
		}
		else {
			throw new CustomException("No user found with id: " + userId);	
		}
	}

	public byte[] getImage(int id) throws IOException {
		Optional<User> user = userRepository.findByUserId(id);
		if(user.isEmpty()) {
            throw new CustomException("No user found with id: " + id);
		}
		String profile_url = user.get().getProfileUrl();
		try {
			String[] parts = profile_url.split("/");
			String bucketName = parts[0];
		    String rootPath = parts[1];
			S3Object obj = s3.getObject(bucketName, rootPath);
			S3ObjectInputStream inputStream = obj.getObjectContent();
			return IOUtils.toByteArray(inputStream);
		}
		catch(AmazonServiceException e) {
			throw new CustomException("Failed to get profile picture from S3");
		}
	}
}
