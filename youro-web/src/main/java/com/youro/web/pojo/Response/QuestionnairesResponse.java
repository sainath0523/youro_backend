package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionnairesResponse {
    private int questionId;
    private String question;
    private String[] options;
    private int weight;
}
