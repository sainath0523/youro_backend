package com.youro.web.pojo.Request;

import com.youro.web.entity.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SymptomScoreRequest {
    @NotNull(message = "is required")
    private int patientId;

    @NotNull(message = "is required")
    private int diagnosisId;

    @NotNull(message = "is required")
    private Map<String, Map<String, String>> questionData;

    @NotNull(message = "is required")
    private Date takenDate;

}
