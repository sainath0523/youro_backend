package com.youro.web.pojo.Request;

import com.youro.web.entity.PrescriptionType;

import java.util.ArrayList;

public class AddDescriptionRequest {


    public ArrayList<presDetails> presDetails;

    public static class presDetails {

        public String name;
        public PrescriptionType type;

        public int diagnosisId;
    }
}
