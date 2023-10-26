package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GetCustomerAvailResponse {

    public Date date;
    public int noOfSlots;
    public List<SlotInfo> slotInfo;


     public static class SlotInfo{
        public int noOfDoctors;
        public Date startTime;

        public ArrayList<Integer> doctorIds;

    }
}
