package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionnairesResponse {
    private int questionId;
    private String question;
    private List<options> options;


    public static class options{
        public int oId;
        public String oName;

    }
}
