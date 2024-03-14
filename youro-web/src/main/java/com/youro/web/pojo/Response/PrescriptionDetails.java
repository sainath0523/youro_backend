package com.youro.web.pojo.Response;

import com.youro.web.entity.PrescriptionType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PrescriptionDetails {

    public int presId;
    public String presName;
    public String dosage;
    public Boolean indicator = false;
    public PrescriptionType type;
    public String shortInfo;
    public String overview;
}