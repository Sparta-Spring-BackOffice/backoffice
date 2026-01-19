package com.example.backoffice.common.dto;

import com.example.backoffice.common.responsecode.SuccessCode;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({ "timestamp", "status", "message", "data" })
public class SuccessResponse<T> {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final T data;

    public SuccessResponse(SuccessCode successCode,  T dto) {
        this.timestamp = LocalDateTime.now();
        this.status = successCode.getStatusCode().value();
        this.message = successCode.getMessage();
        this.data = dto;
    }

    // 필요할 경우 성공 코드의 메세지 대신 추가적으로 메세지 입력 가능
    public SuccessResponse(SuccessCode successCode, String anotherMessage, T dto) {
        this.timestamp = LocalDateTime.now();
        this.status = successCode.getStatusCode().value();
        this.message = anotherMessage;
        this.data = dto;
    }
}
