package com.youro.web.mapper;

import com.youro.web.entity.SymptomScore;
import com.youro.web.pojo.Response.GetSymptomScoreResponse;

import java.util.ArrayList;
import java.util.List;

public  class SymptomScoreMapper {


        public static List<GetSymptomScoreResponse> getSymptomsScore(List<SymptomScore> response)
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
}
