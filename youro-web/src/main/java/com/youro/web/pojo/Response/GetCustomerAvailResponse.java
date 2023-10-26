package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GetCustomerAvailResponse {

    public Date date;
    public int noOfSlots;

    public List<SlotRequest> slotInfo;



}
