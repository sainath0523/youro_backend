package com.youro.web.mapper;

import com.youro.web.entity.Questionnaires;
import com.youro.web.pojo.Response.QuestionnairesResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class QuestionnairesMapper {
    public static List<QuestionnairesResponse> convertQuestionnairesEntityToPojo(List<Questionnaires> dbResponseList){
        List<QuestionnairesResponse> res = new ArrayList<>();
        for(Questionnaires itr : dbResponseList){
            QuestionnairesResponse temp = new QuestionnairesResponse();
            temp.setQuestionId(itr.getQId());
            temp.setQuestion(itr.getQuestion());
            String[] ansStr = itr.getAns().split(",");
            temp.setOptions(ansStr);
            temp.setWeight(itr.getWeight());
            res.add(temp);
        }
        return res;
    }
}
