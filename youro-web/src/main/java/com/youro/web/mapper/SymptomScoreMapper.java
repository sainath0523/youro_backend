package com.youro.web.mapper;

import com.youro.web.entity.Diagnosis;
import com.youro.web.entity.SymptomScore;
import com.youro.web.entity.User;
import com.youro.web.pojo.Request.SymptomScoreRequest;
import com.youro.web.pojo.Response.GetSymptomScoreResponse;
import jdk.jshell.Diag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public  class SymptomScoreMapper {


        public static List<GetSymptomScoreResponse> convertEntityToResPojo(List<SymptomScore> response)
        {
            List<GetSymptomScoreResponse> responses = new ArrayList<>();
            for(SymptomScore res : response)
            {
                GetSymptomScoreResponse sym = new GetSymptomScoreResponse();
                sym.dateTime = res.dateTime;
                sym.diagnosisId = res.getDiagnosis().getDiagId();
                sym.patientId = res.getPatientId().userId;
                sym.symptomScore = res.symptomScore;
                sym.questionData = res.questionData;

                responses.add(sym);

            }
            return responses;

        }

        public static SymptomScore convertReqBodyToEntity(SymptomScoreRequest reqBody){
            SymptomScore res = new SymptomScore();

            User temp= new User();
            temp.setUserId(reqBody.getPatientId());

            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setDiagId(reqBody.getDiagnosisId());

            res.setPatientId(temp);
            //res.setDateTime(reqBody.getTakenDate());
            res.setDiagnosis(diagnosis);

            /*final double[] sum = {0};
            reqBody.getQuestionData().forEach((key, value) -> {
                value.forEach((k1, v1) -> {
                    sum[0] += k1.equals("weight") ?  Double.parseDouble(v1) : 0;
                });
            });*/

            res.setQuestionData(reqBody.getQuestionData().toString());
            res.setSymptomScore(1);
            return res;
        }
}
