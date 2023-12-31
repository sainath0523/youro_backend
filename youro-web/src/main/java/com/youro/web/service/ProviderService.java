package com.youro.web.service;

import com.youro.web.entity.*;
import com.youro.web.exception.CustomException;
import com.youro.web.mapper.DoctorSchToSlotsMapper;
import com.youro.web.mapper.UserMapper;
import com.youro.web.pojo.Request.AddAvailabilityRequest;
import com.youro.web.pojo.Request.UpdateUserRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Response.DoctorAvailabilityResponse;
import com.youro.web.repository.AppointmentsRepository;
import com.youro.web.repository.DoctorScheduleRepository;
import com.youro.web.repository.UserRepository;
import com.youro.web.utils.HelpUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProviderService {
	
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    DoctorScheduleRepository drScheduleRepo;
    
    @Autowired
    AppointmentsRepository appointmentsRepository;
    
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    NotificationService notificationService;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)");


    public User getProfile(int id){
        Optional<User> res = userRepository.findById(id);
        if(res.isEmpty())
        {
            throw new CustomException("User not available");
        }
        return res.get();
    }

    public User updateProfile(UpdateUserRequest userReq){
        Optional<User> temp = userRepository.findById(userReq.userId);
        String actualMailId = temp.get().email;
        User res = new User();
        int exists = userRepository.checkIfEmailExists(userReq.email);
        if(userReq.userId == temp.get().userId){

            res = UserMapper.updateRequestToUser(userReq, temp.get(), passwordEncoder);
            res.userId = temp.get().getUserId();
            if(exists == 1){
                res.setEmail(actualMailId);
            }
            return userRepository.save(res);
        }
        return res;
    }

    @Transactional
    public BasicResponse removeAvailability(AddAvailabilityRequest request) throws ParseException {

        try {
            Date startDate = inputFormat.parse(request.startTime);
            Date startTime = inputFormat.parse(request.startTime);
            Date endTime = inputFormat.parse(request.endTime);
            List<Appointments>  appointments = appointmentsRepository.getDeleteAppointments(request.docId, startTime, endTime);
            appointments.forEach(l -> cancelAppointment(l.getApptId(), l.getDoctorId().getUserId(), false));
            drScheduleRepo.getDoctor(request.docId, startTime, endTime);
            List<DoctorSchedule> schduleList = drScheduleRepo.getDoctorList(request.docId, startTime, endTime);

            if(!schduleList.isEmpty())
            {
            	if(schduleList.size() == 1)
                {
                    DoctorSchedule sche = schduleList.get(0);
                    
                    if(sche.getSchStartTime().compareTo(startTime) == 0)
                    {
                        sche.setSchStartTime(endTime);
                    }
                    else if (sche.getSchEndTime().compareTo(endTime) == 0)
                    {
                        sche.setSchEndTime(startTime);
                    }
                    else
                    {
                        DoctorSchedule newSch = new DoctorSchedule();
                        newSch.setSchEndTime(sche.getSchEndTime());

                        sche.setSchEndTime(startTime);
                        drScheduleRepo.save(sche);
                        newSch.setSchStartTime(endTime);
                        newSch.setDoctorId(sche.getDoctorId());
                        newSch.setSchDate(sche.getSchDate());
                        drScheduleRepo.save(newSch);
                    }
                }
                else
                {
                    schduleList.sort(Comparator.comparing(DoctorSchedule::getSchStartTime).thenComparing(DoctorSchedule::getSchEndTime));
                    DoctorSchedule sche = schduleList.get(0);
                    sche.setSchDate(startDate);
                    sche.setSchEndTime(startTime);
                    drScheduleRepo.save(sche);

                    sche = schduleList.get(1);
                    sche.setSchDate(startDate);
                    sche.setSchStartTime(endTime);
                    drScheduleRepo.save(sche);

                }
            }

            //String scheDate  = outputFormat.format(startDate);
            /*int iop = 0;
            if (iop == 1) {
                List<DoctorSchedule> drList = drScheduleRepo.findByDoctorIdAndSchDate(HelpUtils.getUser(request.docId), startDate);
                List<DoctorSchedule> list = new ArrayList<>();
                DoctorSchedule sche = new DoctorSchedule();
                sche.setSchDate(startDate);
                sche.setSchEndTime(endTime);
                sche.setSchStartTime(startTime);
                User user = new User();
                user.setUserId(request.docId);
                sche.setDoctorId(user);

                for (DoctorSchedule sch : drList) {
                    boolean flag = true;
                    if (sche.getSchStartTime().compareTo(sch.getSchStartTime()) >= 0 && sche.getSchStartTime().compareTo(sch.getSchEndTime()) <= 0) {
                        list.add(sch);
                        flag = false;
                    }
                    if (sche.getSchEndTime().compareTo(sch.getSchStartTime()) >= 0 && sche.getSchEndTime().compareTo(sch.getSchEndTime()) <= 0) {
                        if (flag) {
                            list.add(sch);
                        }
                    }
                }

                if (list.size() == 1) {
                    if (sche.getSchStartTime().compareTo(list.get(0).schStartTime) == 0) {
                        if (sche.getSchEndTime().compareTo(list.get(0).schEndTime) == 0) {
                            drScheduleRepo.deleteById(list.get(0).getSchId());
                        } else {
                            sche.setSchStartTime(sche.schEndTime);
                            sche.setSchEndTime(list.get(0).getSchEndTime());
                            sche.setSchId(list.get(0).schId);
                            drScheduleRepo.save(sche);

                        }
                    } else {
                        if (sche.getSchEndTime().compareTo(list.get(0).schEndTime) == 0) {
                            sche.setSchEndTime(sche.schStartTime);
                            sche.setSchStartTime(list.get(0).schStartTime);
                        } else {
                            DoctorSchedule shcp = new DoctorSchedule();
                            shcp.setSchStartTime(list.get(0).schStartTime);
                            shcp.setSchEndTime(sche.schStartTime);
                            shcp.setDoctorId(sche.getDoctorId());
                            shcp.setSchDate(startDate);
                            drScheduleRepo.save(shcp);
                            sche.setSchStartTime(sche.schEndTime);
                            sche.setSchEndTime(list.get(0).schEndTime);
                        }
                        sche.setSchId(list.get(0).schId);
                        drScheduleRepo.save(sche);
                    }
                } else {
                    List<Integer> rem_List = list.stream().map(DoctorSchedule::getSchId).toList();
                    drList.sort(Comparator.comparing(DoctorSchedule::getSchStartTime).thenComparing(DoctorSchedule::getSchEndTime));

                    for (int i = 0; i < drList.size(); i++) {
                        if (rem_List.contains(drList.get(i).getSchId())) {
                            sche.setSchStartTime(drList.get(i).schStartTime);
                            sche.setSchEndTime(startTime);
                            sche.setSchId(drList.get(i).getSchId());
                            drScheduleRepo.save(sche);
                            i++;
                            while (!rem_List.contains(drList.get(i).getSchId())) {
                                drScheduleRepo.deleteById(drList.get(i).getSchId());
                                i++;
                            }
                            sche.setSchStartTime(endTime);
                            sche.setSchEndTime(drList.get(i).schEndTime);
                            sche.setSchId(drList.get(i).getSchId());
                            drScheduleRepo.save(sche);
                        }
                    }

                }
            }*/
            }catch(Exception e)
            {
                throw new CustomException(e.getLocalizedMessage());
            }

        return  new BasicResponse("Removed Successfully");
    }

    public BasicResponse addAvailability(AddAvailabilityRequest request)
    {
        try {
            inputFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().toZoneId()));
            Date startDate = inputFormat.parse(request.startTime);
            Date startTime = inputFormat.parse(request.startTime);
            Date endTime = inputFormat.parse(request.endTime);
            DoctorSchedule doctorSchedule = new DoctorSchedule();
            User user = new User();
            user.setUserId(request.docId);
            doctorSchedule.setDoctorId(user);
            doctorSchedule.setSchDate(startDate);
            doctorSchedule.setSchEndTime(endTime);
            doctorSchedule.setSchStartTime(startTime);
           // String scheDate  = outputFormat.format(doctorSchedule.schDate);
            List<DoctorSchedule> drList = drScheduleRepo.findByDoctorIdAndSchDate(HelpUtils.getUser(request.docId),doctorSchedule.schDate);
            drList.add(doctorSchedule);
            saveDoctorSchedule(drList);
        }catch (Exception e)
        {
            throw new CustomException(e.getLocalizedMessage());
        }
       return new BasicResponse("Added Successfully");
    }

    public void saveDoctorSchedule(List<DoctorSchedule> scheduleList)
    {
        List<Integer> docList = scheduleList.parallelStream().map(DoctorSchedule :: getSchId).toList();
        scheduleList.sort(Comparator.comparing(DoctorSchedule::getSchStartTime).thenComparing(DoctorSchedule::getSchEndTime));
        scheduleList = mergeSlots(scheduleList);
        ArrayList<Integer> doctorList = new ArrayList<>(docList);
        for(DoctorSchedule sch : scheduleList)
        {
            drScheduleRepo.save(sch);
            doctorList.remove(Integer.valueOf(sch.getSchId()));
        }
        for(int k : doctorList)
        {
            drScheduleRepo.deleteById(k);
        }
    }

    public DoctorAvailabilityResponse getAvailability(int docId)
    {
        DoctorAvailabilityResponse resp = new DoctorAvailabilityResponse();
        List<Appointments> apptList = appointmentsRepository.findAppointments(docId);
        resp.setAppointments(DoctorSchToSlotsMapper.getAppointments(apptList));
        List<DoctorSchedule> schedules= drScheduleRepo.findDoctorAvail(docId);
        resp.setDocAvail(DoctorSchToSlotsMapper.convertDoctorScheduleToAvail(schedules));
        return resp;
    }

    public BasicResponse cancelAppointment(int apptId, int uId, boolean flag) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        try {
        	
        	Optional<User> user = userRepository.findById(uId);
            
            if (user.isEmpty()) {
                throw new CustomException("No user found with id: " + uId);
            }
            
            Optional<Appointments> appointments = appointmentsRepository.findById(apptId);

            if (appointments.isEmpty()) {
                throw new CustomException("Appointment not present");
            }

            Appointments appt = appointments.get();
            User userToCancel = user.get();
            int docId = appt.getDoctorId().getUserId();
            
            int userIdToBeNotified;
            if(userToCancel.userType ==  UserType.PATIENT)
            {
            	userIdToBeNotified = appt.getDoctorId().getUserId();

            }
            else {
            	userIdToBeNotified = appt.getPatientId().getUserId();
            }
            
            User userToBeNotified = userRepository.findById(userIdToBeNotified).get();
            
            String msgBodyConfirmation = "Hi, \nWe are writing to inform you that the appointment scheduled on " 
        			+ appt.getApptDate() + " with "
        			+ userToBeNotified.getFirstName() + " " + userToBeNotified.getLastName() + "has been cancelled successfully. \n\nThanks, \nYouro Team";
            String msgSubjConfirmation = "Confirmation :: Appointment Cancellation";
            
            String msgBodyToNotify = "Hi, \nWe are writing to inform you that the appointment scheduled on " 
        			+ appt.getApptDate() + " with "
        			+ userToCancel.getFirstName() + " " + userToCancel.getLastName() + "has been cancelled. Please plan a schedule for another time.  \n\nThanks, \nYouro Team";
            String msgSubjToNotify = "Important! Appointment Cancellation Alert";
            
            SimpleMailMessage mes = new SimpleMailMessage();
            // Mail confirming cancellation
            mes.setTo(userToCancel.getEmail());
    	    mes.setSubject(msgSubjConfirmation);
    	    mes.setText(msgBodyConfirmation);
    	    javaMailSender.send(mes);
    	    
    	    // Mail notifying cancellation
            mes.setTo(userToBeNotified.getEmail());
    	    mes.setSubject(msgSubjToNotify);
    	    mes.setText(msgBodyToNotify);
    	    javaMailSender.send(mes);
    	    if(flag) {
                DoctorSchedule doctorSchedule = new DoctorSchedule();
                doctorSchedule.setSchDate(appt.getApptDate());
                doctorSchedule.setSchEndTime(appt.getApptEndTime());
                doctorSchedule.setSchStartTime(appt.getApptStartTime());
                User temp = new User();
                temp.setUserId(docId);
                doctorSchedule.setDoctorId(temp);
                //String scheDate  = outputFormat.format(doctorSchedule.schDate);
                List<DoctorSchedule> drList = drScheduleRepo.findByDoctorIdAndSchDate(HelpUtils.getUser(docId), doctorSchedule.schDate);
                drList.add(doctorSchedule);
                saveDoctorSchedule(drList);
            }
            appt.status = AppointmentStatus.CANCELED;
            appointmentsRepository.save(appt);
            notificationService.saveApptNotification(user.get(),appt,"CANCEL");
            resp.message = "Canceled Appointment";
        }catch (Exception e)
        {
            throw new CustomException(e.getLocalizedMessage());
        }
        return resp;
    }

    public BasicResponse updateAppointment(int apptId, String link) throws CustomException
    {
        try {
          Appointments appt = appointmentsRepository.findById(apptId).get();
          appt.setLink(link);
          appointmentsRepository.save(appt);
        }catch (Exception e)
        {
            throw new CustomException(e.getLocalizedMessage());
        }
        return new BasicResponse("Updated Successfully");
    }

    public List<DoctorSchedule> mergeSlots (List<DoctorSchedule> list)
    {
        int index = 0;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(index).schEndTime.compareTo(list.get(i).schStartTime) >=0) {
                list.get(index).schEndTime
                        =  list.get(index).schEndTime.compareTo(list.get(i).schEndTime) > 0 ? list.get(index).schEndTime : list.get(i).schEndTime;
            }
            else {
                index++;
                list.set(index, list.get(i));
            }
        }
        List<DoctorSchedule> list1 = new ArrayList<>();
        for(int i = 0; i <= index ; i++)
        {
            list1.add(list.get(i));
        }
        return list1;
    }

    public List<User> getUsersByDoctor(int uId)
    {
        List<User> users = new ArrayList<>();
        List<Integer> userId = userRepository.getUserByDoctor(uId);
          if(userId !=null && !userId.isEmpty())
          {
              for(int i : userId) {
                  users.add(userRepository.findById(i).get());
              }
          }
          return users;

    }

}