package com.youro.web.mapper;

import com.youro.web.entity.Appointments;
import com.youro.web.entity.DoctorSchedule;
import com.youro.web.pojo.Response.*;
import com.youro.web.utils.HelpUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DoctorSchToSlotsMapper {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)");

    public static List<AvailableSlotsByDateResponse> convertDoctorSchToSlots(List<DoctorSchedule> inp){
        List<AvailableSlotsByDateResponse> res = new ArrayList<>();
        AvailableSlotsByDateResponse temp;
        for(DoctorSchedule itr : inp){
            temp = new AvailableSlotsByDateResponse();
            temp.setDoctorId(itr.getDoctorId().getUserId());
            temp.setGender(itr.getDoctorId().getGender().toString());
            temp.setStartTime(itr.getSchStartTime());
            res.add(temp);
        }
        return res;
    }

    public static List<DoctorAvailabilityResponse.DoctorAvailability> convertDoctorScheduleToAvail(List<DoctorSchedule> doctorSchedules)
    {
        List<DoctorAvailabilityResponse.DoctorAvailability> resp = new ArrayList<>();
        for(DoctorSchedule sch : doctorSchedules)
        {
            DoctorAvailabilityResponse.DoctorAvailability avail = new DoctorAvailabilityResponse.DoctorAvailability();
            avail.doctorId = sch.getDoctorId().getUserId();
            avail.startTime = HelpUtils.convertDateTime(sch.schDate, sch.schStartTime, "");
            avail.endTime = HelpUtils.convertDateTime(sch.schDate, sch.schEndTime, "");
            avail.status = "Available";
            resp.add(avail);
        }
        return resp;
    }

    public static List<AppointmentResponse> getAppointments(List<Appointments> response)
    {
        List<AppointmentResponse> responses = new ArrayList<>();
        for(Appointments app : response)
        {
            AppointmentResponse res = new AppointmentResponse();
            String patPrefix = app.getPatientId().getGender().toString().equals("MALE") ? "Mr. " : "Ms. ";
            String docPrefix = app.getDoctorId().getGender().toString().equals("MALE") ? "Mr. " : "Ms. ";
            res.apptId = app.apptId;
            res.apptStartTime= HelpUtils.convertDateTime(app.apptDate, app.apptStartTime, "");
            res.apptEndTime = HelpUtils.convertDateTime(app.apptDate, app.apptEndTime, "");
            res.link = app.link;
            res.doctorId = app.getDoctorId().userId;
            res.patientId = app.getPatientId().userId;
            res.doctorName = docPrefix + app.getDoctorId().firstName + " " + app.getDoctorId().lastName;
            res.patientName = patPrefix + app.getPatientId().firstName + " " + app.getPatientId().lastName;
            responses.add(res);
        }
        return responses;
    }

    public  static List<GetCustomerAvailResponse> getCustomerAvailResponse(Map<Date, List<SlotInfo>> response, String timeZone) throws ParseException {

        inputFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        List<GetCustomerAvailResponse> resp = new ArrayList<>();
        Map<String, List<SlotRequest>> repo = new HashMap<>();
        for (Date date : response.keySet()) {
            List<SlotInfo> list = new ArrayList<>(response.get(date));
            list.sort(Comparator.comparing(SlotInfo::getStartTime));
            for (SlotInfo info : list) {
                SlotRequest slot_req = new SlotRequest();
                slot_req.startTime = inputFormat.format(info.startTime);

                String date1 = dateFormat.format(inputFormat.parse(slot_req.startTime));

                slot_req.noOfDoctors = info.noOfDoctors;
                slot_req.doctorIds = info.doctorIds;
                List<SlotRequest> list1;
                if(repo.containsKey(date1))
                {
                    list1 = repo.get(date1);
                }else {
                    list1 = new ArrayList<>();
                }
                list1.add(slot_req);
                repo.put(date1, list1);
            }
        }
        for(String date : repo.keySet())
        {
            GetCustomerAvailResponse res = new GetCustomerAvailResponse();
            res.date = date;
            res.setNoOfSlots(repo.get(date).size());
            List<SlotRequest> list = new ArrayList<>(repo.get(date));
            list.sort(Comparator.comparing(SlotRequest::getStartTime));
            res.setSlotInfo(list);
            resp.add(res);
        }
        return resp;
    }
}