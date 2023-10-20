package com.youro.web.service;

import com.youro.web.entity.User;
import com.youro.web.mapper.UserMapper;
import com.youro.web.pojo.Request.UpdateUserRequest;
import com.youro.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.youro.web.entity.AppointmentStatus;
import com.youro.web.entity.Appointments;
import com.youro.web.entity.DoctorSchedule;
import com.youro.web.entity.User;

import java.time.LocalTime;
import java.util.TimeZone;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.Request.AddAvailabilityRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.repository.AppointmentsRepository;
import com.youro.web.repository.DoctorScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("+++++=========+++++");
        System.out.println("+++++=========+++++");
        Optional<User> temp = userRepository.findByEmail(user.email);
        System.out.println("+++++=========+++++");
        System.out.println("+++++=========+++++");
        System.out.println(temp.get().getEmail());
        System.out.println("+++++=========+++++");
        System.out.println("+++++=========+++++");

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

     /*       inputFormat.setTimeZone(TimeZone.getTimeZone("GMT-0400"));
            outputFormat.setTimeZone(TimeZone.getTimeZone("GMT-0400"));
            timeFormat.setTimeZone(TimeZone.getTimeZone("GMT-0400"));*/
            Date startDate = inputFormat.parse(request.stateTime);
            Date startTime = inputFormat.parse(request.stateTime);
            Date endTime = inputFormat.parse(request.endTime);
            String scheDate  = outputFormat.format(startDate);
            List<DoctorSchedule> drList = drScheduleRepo.findByDoctorIdAndSchDate(request.dotId, scheDate);

            Set<DoctorSchedule> list = new HashSet<>();

            DoctorSchedule sche = new DoctorSchedule();
            sche.setSchDate(outputFormat.parse(outputFormat.format(startDate)));
            sche.setSchEndTime(timeFormat.parse(timeFormat.format(endTime)));
            sche.setSchStartTime(timeFormat.parse(timeFormat.format(startTime)));


            for(DoctorSchedule sch : drList)
            {
                /*LocalTime time1 = sch.getSchStartTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
                LocalTime time2 = sch.getSchEndTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
                LocalTime time3 = sche.getSchStartTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
                LocalTime time4 = sche.getSchEndTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
*/

                if(sche.getSchStartTime().compareTo(sch.getSchStartTime()) >=0 && sche.getSchStartTime().compareTo(sch.getSchEndTime()) <=0)
                {
                    list.add(sch);
                }
                if(sche.getSchEndTime().compareTo(sch.getSchStartTime()) >=0 && sche.getSchEndTime().compareTo(sch.getSchEndTime()) <=0)
                {
                    list.add(sch);
                }
            }
            System.out.println(list);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  resp;
    }


    public BasicResponse addAvailability(AddAvailabilityRequest request)
    {
        BasicResponse resp = new BasicResponse();
        try {
            inputFormat.setTimeZone(TimeZone.getTimeZone("GMT-0400"));

            Date startDate = inputFormat.parse(request.stateTime);
            Date startTime = inputFormat.parse(request.stateTime);
            Date endTime = inputFormat.parse(request.endTime);

            DoctorSchedule doctorSchedule = new DoctorSchedule();
            User user = new User();
            user.setUserId(request.dotId);
            doctorSchedule.setDoctorId(user);
            doctorSchedule.setSchDate(new SimpleDateFormat("yyyy-MM-dd").parse(outputFormat.format(startDate)));
            doctorSchedule.setSchEndTime(new SimpleDateFormat("HH:mm:ss").parse(timeFormat.format(endTime)));
            doctorSchedule.setSchStartTime(new SimpleDateFormat("HH:mm:ss").parse(timeFormat.format(startTime)));
            String scheDate  = outputFormat.format(doctorSchedule.schDate);
            List<DoctorSchedule> drList = drScheduleRepo.findByDoctorIdAndSchDate(request.dotId,scheDate);
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
        int index = 0; // Stores index of last element
        int size = list.size();
        // in output array (modified arr[])

        // Traverse all input Intervals
        for (int i = 1; i < list.size(); i++) {
            // If this is not first Interval and overlaps
            // with the previous one
            if (list.get(index).schEndTime.compareTo(list.get(i).schStartTime) >=0) {
                // Merge previous and current Intervals
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
