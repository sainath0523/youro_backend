package com.youro.web.service;

import com.youro.web.entity.*;
import com.youro.web.exception.CustomException;
import com.youro.web.mapper.NotificationMapper;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Response.GetNotificationsResponse;
import com.youro.web.repository.AppointmentsRepository;
import com.youro.web.repository.DiagnosisRepository;
import com.youro.web.repository.NotificationRepository;
import com.youro.web.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service

public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    AppointmentsRepository appointmentsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DiagnosisRepository diagnosisRepository;
    
    static SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    public List<GetNotificationsResponse> getNotifications(int uId, String timeZone)
    {
        return NotificationMapper.EntityToResponseMapper(notificationRepository.getByUser(uId), timeZone);
    }

    @Transactional
    public BasicResponse deleteNotifications(int notId)
    {
        try {
            notificationRepository.deleteById(notId);
            return new BasicResponse("Deleted Successfully");
        }catch (Exception e)
        {
            throw new CustomException(e.getLocalizedMessage());
        }
    }

    @Transactional
   	public BasicResponse deleteAllNotifications(int userId) {
    	try {
            notificationRepository.deleteByUser(userId);
            return new BasicResponse("Deleted Successfully");
        }catch (Exception e)
        {
            throw new CustomException(e.getLocalizedMessage());
        }
   	}
    
    public void saveCarePlanNotification(Appointments appt, CarePlan carePlan, String type)
    {
        Notification notification = new Notification();
        appt = appointmentsRepository.findById(appt.getApptId()).get();
        notification.setUserId(appt.getPatientId());
        User doctor = userRepository.findById(appt.getDoctorId().getUserId()).get();
        Diagnosis diagnosis = diagnosisRepository.findById(appt.getDiagnosis().getDiagId()).get();
        String message = "";
        if(type.equals("NEW")) {
            message = "Care Plan Issued - Your recent appointment with Dr. " + doctor.lastName + " has a new care plan for " + diagnosis.getName() + ". Please check your profile for details";
        }else
        {
            message = "Care Plan Updated - Dr.  " + doctor.lastName + " has modified your care plan for " + diagnosis.getName() + ". Kindly review the changes in your profile.";
        }
        notification.setMessage(message);
        notification.setDateTime(new Date());
        notificationRepository.save(notification);
    }

    public void saveApptNotification(User user, Appointments appt, String type)
    {
        String message = "";
        User notUser = null;
        User printUser = null;
        Diagnosis diagnosis = diagnosisRepository.findById(appt.getDiagnosis().getDiagId()).get();
        if(type.equals("BOOKED"))
        {
            notUser = userRepository.findById(appt.getDoctorId().getUserId()).get();
            printUser = userRepository.findById(appt.getPatientId().getUserId()).get();
            message = "New Booking Alert - " + printUser.getFirstName() + "  on &&" + appt.getApptStartTime() + "&& for " + diagnosis.getName();
        }else
        {
            if(user.userType == UserType.PATIENT)
            {
                notUser = userRepository.findById(appt.getDoctorId().getUserId()).get();
                printUser = userRepository.findById(appt.getPatientId().getUserId()).get();
            }else {
                printUser = userRepository.findById(appt.getDoctorId().getUserId()).get();
                notUser = userRepository.findById(appt.getPatientId().getUserId()).get();
            }
            message = "New Cancellation Alert - " + printUser.getFirstName() + "  on &&" + inputFormat.format(appt.getApptStartTime()) + "&&";
        }
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUserId(notUser);
        notification.setDateTime(new Date());
        notificationRepository.save(notification);
    }
   
}