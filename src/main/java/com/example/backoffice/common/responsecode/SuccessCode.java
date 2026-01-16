package com.example.backoffice.common.responsecode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃에 성공하였습니다."),
    CREATE_SUCCESS(HttpStatus.CREATED,"데이터 생성에 성공하였습니다"),
    READ_SUCCESS(HttpStatus.OK,"데이터 조회에 성공하였습니다"),
    UPDATE_SUCCESS(HttpStatus.OK,"데이터 수정에 성공하였습니다"),
    DELETE_SUCCESS(HttpStatus.OK,"데이터 삭제에 성공하였습니다");

    private final HttpStatusCode statusCode;
    private final String message;
}
