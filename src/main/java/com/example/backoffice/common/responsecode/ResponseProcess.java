package com.example.backoffice.common.responsecode;

import com.example.backoffice.common.dto.SuccessResponse;
import org.springframework.http.ResponseEntity;

public class ResponseProcess {

    public static <T> ResponseEntity<SuccessResponse<T>> responseWithBody(SuccessCode successCode, T dto){
        return ResponseEntity.status(successCode.getStatusCode()).body(new SuccessResponse<>(successCode, dto));
    }

    public static <T> ResponseEntity<SuccessResponse<T>> responseWithBodyNewMessage(SuccessCode successCode, T dto, String message){
        return ResponseEntity.status(successCode.getStatusCode()).body(new SuccessResponse<>(successCode, message, dto));
    }

    public static <T> ResponseEntity<SuccessResponse<T>> responseWithBuild(SuccessCode successCode, T dto){
        return ResponseEntity.status(successCode.getStatusCode()).body(new SuccessResponse<>(successCode, null));
    }

    public static <T> ResponseEntity<SuccessResponse<T>> responseWithBuildNewMessage(SuccessCode successCode, T dto, String message){
        return ResponseEntity.status(successCode.getStatusCode()).body(new SuccessResponse<>(successCode, message,null));
    }
}
