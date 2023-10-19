package com.youro.web.pojo.Request;

import com.youro.web.pojo.Response.PrescriptionDetails;

import java.util.ArrayList;

public class SaveCarePlanRequest {

    public String apptId;
    public ArrayList<PrescriptionDetails> vitamins;
    public ArrayList<PrescriptionDetails> medicines;

    public ArrayList<PrescriptionDetails> lifeStyle;

    public ArrayList<PrescriptionDetails> labs;

    public ArrayList<PrescriptionDetails> imaging;
}
