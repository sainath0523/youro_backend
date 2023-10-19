package com.youro.web.pojo.Request;

import com.youro.web.entity.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

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
    private Map<String, Map<String, String>> questionData;

    @NotNull(message = "is required")
    private Date takenDate;

}
