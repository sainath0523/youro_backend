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
        Map<Integer, List<String>> optionsByQID = new HashMap<>();

        for (Options option : optionsList) {
            int qID = option.questionnaires.getQId();
            optionsByQID.computeIfAbsent(qID, k -> new ArrayList<>()).add(option.getOptionName());
        }

        for(Questionnaires itr : dbResponseList){
            QuestionnairesResponse temp = new QuestionnairesResponse();
            temp.setQuestionId(itr.getQId());
            temp.setQuestion(itr.getQuestion());
            String[] ansStr = optionsByQID.get(itr.getQId()).toArray(new String[0]);
            temp.setOptions(ansStr);
            res.add(temp);
        }
        return res;
    }
}
