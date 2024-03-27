package com.youro.web.pojo.Response;

import com.youro.web.entity.PrescriptionType;
import com.youro.web.entity.Category;
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
    public Category category;
    public String shortInfo;
    public String overview;
    public String categoryName;
}