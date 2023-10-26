package com.youro.web.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youro.web.entity.Diagnosis;
import com.youro.web.entity.SymptomScore;
import com.youro.web.entity.User;
import com.youro.web.pojo.Request.SymptomScoreRequest;
import com.youro.web.pojo.Response.GetSymptomScoreResponse;
import com.youro.web.utils.HelpUtils;
import jdk.jshell.Diag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
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
                sym.scoreId = res.scoreId;
                sym.dateTime = res.dateTime;
                sym.diagnosisId = res.getDiagnosis().getDiagId();
                sym.patientId = res.getPatientId().userId;
                sym.symptomScore = res.symptomScore;
                sym.questionData = res.questionData;
                responses.add(sym);

            }
            return responses;

        }

        public static SymptomScore convertReqBodyToEntity(SymptomScoreRequest reqBody, int score){
            SymptomScore res = new SymptomScore();
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setDiagId(reqBody.getDiagnosisId());
            res.setPatientId(HelpUtils.getUser(reqBody.getPatientId()));
            res.setDiagnosis(diagnosis);
            ObjectMapper mapper = new ObjectMapper();
            try {
                String json = mapper.writeValueAsString(reqBody.getQuestionData());
                res.setQuestionData(json);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            res.setSymptomScore(score);
            res.setDateTime(new Date());
            return res;
        }
}
