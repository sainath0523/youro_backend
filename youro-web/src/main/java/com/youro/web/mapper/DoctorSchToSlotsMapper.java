package com.youro.web.mapper;

import com.youro.web.entity.DoctorSchedule;
import com.youro.web.pojo.Response.AvailableSlotsByDateResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
}
