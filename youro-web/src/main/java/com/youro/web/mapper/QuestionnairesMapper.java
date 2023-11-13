package com.youro.web.mapper;


import com.youro.web.entity.Options;
import com.youro.web.entity.Questionnaires;
import com.youro.web.pojo.Response.QuestionnairesResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuestionnairesMapper {
    public static List<QuestionnairesResponse> convertQuestionnairesEntityToPojo(List<Questionnaires> dbResponseList, List<Options> optionsList){
        List<QuestionnairesResponse> res = new ArrayList<>();
        Map<Integer, List<QuestionnairesResponse.options>> optionsByQID = new HashMap<>();

        for (Options option : optionsList) {
            QuestionnairesResponse.options op = new QuestionnairesResponse.options();
            int qID = option.questionnaires.getQId();
            op.oId = option.oId;
            op.oName = option.optionName;
            optionsByQID.computeIfAbsent(qID, k -> new ArrayList<>()).add(op);
        }

        for(Questionnaires itr : dbResponseList){
            QuestionnairesResponse temp = new QuestionnairesResponse();
            temp.setQuestionId(itr.getQId());
            temp.setQuestion(itr.getQuestion());
            temp.setOptions(optionsByQID.get(itr.getQId()));
            res.add(temp);
        }
        return res;
    }
}
