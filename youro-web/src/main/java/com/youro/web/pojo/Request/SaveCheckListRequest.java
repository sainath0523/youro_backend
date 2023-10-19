package com.youro.web.pojo.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveCheckListRequest {

    @NotNull(message = "is Required")
    public int apptId;
    public Boolean orders;
    public Boolean followUp;
    public Boolean notes;

}
