package com.example.backoffice.common.responsecode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공하였습니다."),;

    private final HttpStatusCode statusCode;
    private final String message;
}
