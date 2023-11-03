package com.youro.web.mapper;

import com.youro.web.entity.Appointments;
import com.youro.web.entity.DoctorSchedule;
import com.youro.web.pojo.Response.*;
import com.youro.web.utils.HelpUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

@Component
public class DoctorSchToSlotsMapper {

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
            avail.startTime = HelpUtils.convertDateTime(sch.schDate, sch.schStartTime);
            avail.endTime = HelpUtils.convertDateTime(sch.schDate, sch.schEndTime);
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
            res.apptStartTime= HelpUtils.convertDateTime(app.apptDate, app.apptStartTime);
            res.apptEndTime = HelpUtils.convertDateTime(app.apptDate, app.apptEndTime);
            res.link = app.link;
            res.doctorId = app.getDoctorId().userId;
            res.patientId = app.getPatientId().userId;
            res.doctorName = docPrefix + app.getDoctorId().firstName + " " + app.getDoctorId().lastName;
            res.patientName = patPrefix + app.getPatientId().firstName + " " + app.getPatientId().lastName;
            responses.add(res);
        }
        return responses;
    }


    public  static List<GetCustomerAvailResponse> getCustomerAvailResponse(Map<Date, List<SlotInfo>> response) throws ParseException {
        List<GetCustomerAvailResponse> resp = new ArrayList<>();
        for(Date date : response.keySet())
        {
            List<SlotRequest> slotInfo = new ArrayList<>();
            GetCustomerAvailResponse res = new GetCustomerAvailResponse();
            res.date = date;
            res.noOfSlots = response.get(date).size();
            List<SlotInfo> list = new ArrayList<>(response.get(date));
            list.sort(Comparator.comparing(SlotInfo :: getStartTime));
            for(SlotInfo info : list)
            {
                SlotRequest slot_req = new SlotRequest();
                slot_req.startTime = HelpUtils.convertDateTime(date, info.startTime);
                slot_req.noOfDoctors = info.noOfDoctors;
                slot_req.doctorIds = info.doctorIds;
                slotInfo.add(slot_req);
            }
            res.slotInfo = slotInfo;
            resp.add(res);
        }
        return resp;
    }
}