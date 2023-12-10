package com.youro.web.pojo.Response;

import com.youro.web.entity.DoctorStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorStatusResponse {

    public int uId;
    public DoctorStatus status;
}