package com.youro.web.mapper;

import com.youro.web.entity.Diagnosis;
import com.youro.web.pojo.Response.DiagnosisResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DiagnosisMapper {
    public static List<DiagnosisResponse> convertDiagnosisEntityToPojo(List<Diagnosis> dbResponseList){
        List<DiagnosisResponse> res = new ArrayList<>();
        for(Diagnosis itr: dbResponseList){
            DiagnosisResponse temp = new DiagnosisResponse();
            temp.setDiagId(itr.getDiagId());
            temp.setName(itr.getName());
            temp.setInfo(itr.getInfo());
            res.add(temp);
        }
        return res;
    }
}
