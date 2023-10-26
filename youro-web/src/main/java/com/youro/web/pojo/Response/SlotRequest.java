package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class SlotRequest {
        public int noOfDoctors;

        public String startTime;

        public ArrayList<Integer> doctorIds;

}
