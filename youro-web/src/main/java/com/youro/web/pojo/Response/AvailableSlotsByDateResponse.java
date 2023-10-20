package com.youro.web.pojo.Response;

import com.youro.web.entity.User;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AvailableSlotsByDateResponse {

    private int doctorId;

    private String gender;

    @Temporal(TemporalType.TIME)
    private Date startTime;

}
