package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetCarePlaneDetails {


    public List<PrescriptionDetails> vitamins = new ArrayList<>();
    public List<PrescriptionDetails> medicines = new ArrayList<>();

    public List<PrescriptionDetails> lifeStyle = new ArrayList<>();

    public List<PrescriptionDetails> labs = new ArrayList<>();

    public List<PrescriptionDetails> imaging  = new ArrayList<>();

}