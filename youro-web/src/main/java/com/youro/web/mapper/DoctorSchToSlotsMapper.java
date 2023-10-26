package com.youro.web.mapper;

import com.youro.web.entity.Appointments;
import com.youro.web.entity.DoctorSchedule;
import com.youro.web.pojo.Response.AvailableSlotsByDateResponse;
import com.youro.web.pojo.Response.DoctorAvailabilityResponse;
import com.youro.web.pojo.Response.GetAppointmentsResponse;
import com.youro.web.pojo.Response.GetCustomerAvailResponse;
import com.youro.web.utils.HelpUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class DoctorSchToSlotsMapper {

    static SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)");
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

    public static List<GetAppointmentsResponse> getAppointments(List<Appointments> response)
    {
        List<GetAppointmentsResponse> responses = new ArrayList<>();
        for(Appointments app : response)
        {
            GetAppointmentsResponse res = new GetAppointmentsResponse();
            String patPrefix = app.getPatientId().getGender().toString().equals("MALE") ? "Mr. " : "Ms. ";
            String docPrefix = app.getDoctorId().getGender().toString().equals("MALE") ? "Mr. " : "Ms. ";
            res.apptId = app.apptId;
            res.apptStartTime= HelpUtils.convertDateTime(app.apptDate, app.apptStartTime); ;
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


    public static List<GetCustomerAvailResponse> getCustomerAvailResponse(Map<Date, List<GetCustomerAvailResponse.SlotInfo>> response) throws ParseException {
        List<GetCustomerAvailResponse> resp = new ArrayList<>();
        for(Date date : response.keySet())
        {
            List<GetCustomerAvailResponse.SlotInfo> slotInfo = new ArrayList<>();
            GetCustomerAvailResponse res = new GetCustomerAvailResponse();
            res.date = date;
            res.noOfSlots = response.get(date).size();
            for(GetCustomerAvailResponse.SlotInfo info : response.get(date))
            {
                info.startTime = outputFormat.parse(HelpUtils.convertDateTime(date, info.startTime));
                slotInfo.add(info);
            }
            res.slotInfo = slotInfo;
            resp.add(res);
        }
        return resp;
    }
}
