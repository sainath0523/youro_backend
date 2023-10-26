package com.youro.web.pojo.Request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SymptomScoreRequest {
    @NotNull(message = "is required")
    private int patientId;

    @NotNull(message = "is required")
    private int diagnosisId;

    @NotNull(message = "is required")
    private List<questionData> questionData;

    public static class questionData {
        public int qId;

        public String question;

        public List<optionsData> optionsData;
    }

    public static class optionsData {

        public int oId;

        public String optionName;
    }

}
