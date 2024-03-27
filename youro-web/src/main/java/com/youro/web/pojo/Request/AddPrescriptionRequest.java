package com.youro.web.pojo.Request;

import com.youro.web.entity.PrescriptionType;

public class AddPrescriptionRequest {


        public String name;
        //public PrescriptionType type;
        public int type;
        public int categoryId;
        public int[] diagnosisId;
        public String shortInfo;
        public String overview;

}