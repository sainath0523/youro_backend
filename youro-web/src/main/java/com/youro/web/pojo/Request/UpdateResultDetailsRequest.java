package com.youro.web.pojo.Request;

import com.youro.web.pojo.Response.UploadResultsResponse;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public class UpdateResultDetailsRequest {

    @NotNull(message = "is Required")
    public int apptId;
    public ArrayList<UploadResultsResponse> fileData;
}
