package com.youro.web.service;

import com.youro.web.entity.User;
import com.youro.web.mapper.AppointmentMapper;
import com.youro.web.mapper.DoctorSchToSlotsMapper;
import com.youro.web.mapper.UserMapper;
import com.youro.web.pojo.Request.UpdateUserRequest;
import com.youro.web.pojo.Response.DoctorAvailabilityResponse;
import com.youro.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.youro.web.entity.AppointmentStatus;
import com.youro.web.entity.Appointments;
import com.youro.web.entity.DoctorSchedule;

import java.util.TimeZone;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.Request.AddAvailabilityRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.repository.AppointmentsRepository;
import com.youro.web.repository.DoctorScheduleRepository;


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



    SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)");
    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    PasswordEncoder passwordEncoder;

    public User getProfile(String email){
        Optional<User> res = userRepository.findByEmail(email);
        return res.get();
    }

    public User updateProfile(UpdateUserRequest user){
        Optional<User> temp = userRepository.findByEmail(user.email);
        User res = new User();
        if(user.email.equals(temp.get().email)){
            res = UserMapper.updateRequestToUser(user, passwordEncoder);
            res.userId = temp.get().getUserId();
            return userRepository.save(res);
        }
        return res;
    }
   
    public BasicResponse removeAvailability(AddAvailabilityRequest request) throws ParseException {

        BasicResponse resp = new BasicResponse();
        try {
            Date startDate = inputFormat.parse(request.startTime);
            Date startTime = inputFormat.parse(request.startTime);
            Date endTime = inputFormat.parse(request.endTime);
            String scheDate  = outputFormat.format(startDate);
            List<DoctorSchedule> drList = drScheduleRepo.findByDoctorIdAndSchDate(request.docId, scheDate);
            List<DoctorSchedule> list = new ArrayList<>();
            DoctorSchedule sche = new DoctorSchedule();
            sche.setSchDate(outputFormat.parse(outputFormat.format(startDate)));
            sche.setSchEndTime(timeFormat.parse(timeFormat.format(endTime)));
            sche.setSchStartTime(timeFormat.parse(timeFormat.format(startTime)));
            User user = new User();
            user.setUserId(request.docId);
            sche.setDoctorId(user);

            for(DoctorSchedule sch : drList) {
                boolean flag = true;
                if (sche.getSchStartTime().compareTo(sch.getSchStartTime()) >= 0 && sche.getSchStartTime().compareTo(sch.getSchEndTime()) <= 0) {
                        list.add(sch);
                        flag = false;
                }
                if (sche.getSchEndTime().compareTo(sch.getSchStartTime()) >= 0 && sche.getSchEndTime().compareTo(sch.getSchEndTime()) <= 0) {
                    if(flag) {
                        list.add(sch);
                    }
                }
            }

            if(list.size() ==1)
            {
               if(sche.getSchStartTime().compareTo(list.get(0).schStartTime) == 0 )
               {
                   if(sche.getSchEndTime().compareTo(list.get(0).schEndTime) == 0) {
                       drScheduleRepo.deleteById(list.get(0).getSchId());
                   }
                   else
                   {
                       sche.setSchStartTime(timeFormat.parse(timeFormat.format(sche.schEndTime)));
                       sche.setSchEndTime(timeFormat.parse(timeFormat.format(list.get(0).getSchEndTime())));
                       sche.setSchId(list.get(0).schId);
                       drScheduleRepo.save(sche);

                   }
               }
               else
               {
                   if(sche.getSchEndTime().compareTo(list.get(0).schEndTime) == 0) {
                       sche.setSchEndTime(timeFormat.parse(timeFormat.format(sche.schStartTime)));
                       sche.setSchStartTime(timeFormat.parse(timeFormat.format(list.get(0).schStartTime)));
                   }
                   else
                   {
                       DoctorSchedule shcp = new DoctorSchedule();
                       shcp.setSchStartTime(timeFormat.parse(timeFormat.format(list.get(0).schStartTime)));
                       shcp.setSchEndTime(timeFormat.parse(timeFormat.format(sche.schStartTime)));
                       shcp.setDoctorId(sche.getDoctorId());
                       shcp.setSchDate(timeFormat.parse(timeFormat.format(sche.schDate)));
                       drScheduleRepo.save(shcp);
                       sche.setSchStartTime(timeFormat.parse(timeFormat.format(sche.schEndTime)));
                       sche.setSchEndTime(timeFormat.parse(timeFormat.format(list.get(0).schEndTime)));
                   }
                   sche.setSchId(list.get(0).schId);
                   drScheduleRepo.save(sche);
               }
            }
            else
            {
               List<Integer> rem_List=  list.stream().map(DoctorSchedule :: getSchId).toList();
                drList.sort(Comparator.comparing(DoctorSchedule::getSchStartTime).thenComparing(DoctorSchedule::getSchEndTime));

                for(int i = 0; i< drList.size() ; i++)
                {
                    if(rem_List.contains(drList.get(i).getSchId()))
                    {
                        sche.setSchStartTime(timeFormat.parse(timeFormat.format(drList.get(i).schStartTime)));
                        sche.setSchEndTime(timeFormat.parse(timeFormat.format(startTime)));
                        sche.setSchId(drList.get(i).getSchId());
                        drScheduleRepo.save(sche);
                        i++;
                        while(!rem_List.contains(drList.get(i).getSchId()))
                        {
                            drScheduleRepo.deleteById(drList.get(i).getSchId());
                            i++;
                        }
                        sche.setSchStartTime(timeFormat.parse(timeFormat.format(endTime)));
                        sche.setSchEndTime(timeFormat.parse(timeFormat.format(drList.get(i).schEndTime)));
                        sche.setSchId(drList.get(i).getSchId());
                        drScheduleRepo.save(sche);
                    }
                }
                resp.message = "Removed Successfully";
            }

            System.out.println(list);
        }catch (Exception e)
        {
            throw new CustomException(e.getLocalizedMessage());
        }
        return  resp;
    }

    public BasicResponse addAvailability(AddAvailabilityRequest request)
    {
        BasicResponse resp = new BasicResponse();
        try {
            inputFormat.setTimeZone(TimeZone.getTimeZone("GMT-0400"));

            Date startDate = inputFormat.parse(request.startTime);
            Date startTime = inputFormat.parse(request.startTime);
            Date endTime = inputFormat.parse(request.endTime);
            DoctorSchedule doctorSchedule = new DoctorSchedule();
            User user = new User();
            user.setUserId(request.docId);
            doctorSchedule.setDoctorId(user);
            doctorSchedule.setSchDate(new SimpleDateFormat("yyyy-MM-dd").parse(outputFormat.format(startDate)));
            doctorSchedule.setSchEndTime(new SimpleDateFormat("HH:mm:ss").parse(timeFormat.format(endTime)));
            doctorSchedule.setSchStartTime(new SimpleDateFormat("HH:mm:ss").parse(timeFormat.format(startTime)));
            String scheDate  = outputFormat.format(doctorSchedule.schDate);
            List<DoctorSchedule> drList = drScheduleRepo.findByDoctorIdAndSchDate(request.docId,scheDate);
            drList.add(doctorSchedule);
            saveDoctorSchedule(drList);
            System.out.println(drList);
            resp.message = "Added successfully";
            return resp;
        }catch (Exception e)
        {
            throw new CustomException(e.getLocalizedMessage());
        }
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
        resp.setAppoitments(DoctorSchToSlotsMapper.getAppointments(apptList));
        List<DoctorSchedule> schedules= drScheduleRepo.findDoctorAvail(docId);
        resp.setDocAvail(DoctorSchToSlotsMapper.convertDoctorScheduleToAvail(schedules));
        return resp;
    }

    public BasicResponse cancelAppointment(int apptId, int docId) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        try {
            inputFormat.setTimeZone(TimeZone.getTimeZone("GMT-0400"));
            Optional<Appointments> appointments = appointmentsRepository.findById(apptId);

            if (appointments.isEmpty()) {
                throw new CustomException("Appointment not present");
            }
            Appointments appt = appointments.get();
            DoctorSchedule doctorSchedule = new DoctorSchedule();
            doctorSchedule.setSchDate(new SimpleDateFormat("yyyy-MM-dd").parse(outputFormat.format(appt.getApptDate())));
            doctorSchedule.setSchEndTime(new SimpleDateFormat("HH:mm:ss").parse(timeFormat.format(appt.getApptEndTime())));
            doctorSchedule.setSchStartTime(new SimpleDateFormat("HH:mm:ss").parse(timeFormat.format(appt.getApptStartTime())));
            User user = new User();
            user.setUserId(docId);
            String scheDate  = outputFormat.format(doctorSchedule.schDate);
            List<DoctorSchedule> drList = drScheduleRepo.findByDoctorIdAndSchDate(docId, scheDate);
            drList.add(doctorSchedule);
            saveDoctorSchedule(drList);
            appt.status = AppointmentStatus.CANCELED;
            appointmentsRepository.save(appt);
            resp.message = "Canceled Appointment";
        }catch (Exception e)
        {
            throw new CustomException(e.getLocalizedMessage());
        }
        return resp;
    }

    public List<DoctorSchedule> mergeSlots (List<DoctorSchedule> list)
    {
        int index = 0;
        int size = list.size();
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
        for(int i = index+1; i < size ; i++)
        {
            list.remove(index);
        }
        return list;
    }
}
