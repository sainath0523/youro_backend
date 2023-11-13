package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class SlotInfo {

    public int noOfDoctors;

    public Date startTime;
    public ArrayList<Integer> doctorIds;
}
