package com.example.backoffice.common.responsecode;

import com.example.backoffice.common.dto.SuccessResponse;
import org.springframework.http.ResponseEntity;

public class ResponseProcess {

    public static <T> ResponseEntity<SuccessResponse<T>> responseWithBody(SuccessCode successCode, T dto){
        return ResponseEntity.status(successCode.getStatusCode()).body(new SuccessResponse<>(successCode, dto));
    }

    public static ResponseEntity<SuccessResponse<Void>> responseWithBuild(SuccessCode successCode){
        return ResponseEntity.status(successCode.getStatusCode()).body(new SuccessResponse<>(successCode, null));
    }
}
