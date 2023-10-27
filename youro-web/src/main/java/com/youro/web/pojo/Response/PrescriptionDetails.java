package com.youro.web.pojo.Response;

import com.youro.web.entity.PrescriptionType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PrescriptionDetails {

    public int presId;
    public String name;
    public String dosage;
    public PrescriptionType type;
}