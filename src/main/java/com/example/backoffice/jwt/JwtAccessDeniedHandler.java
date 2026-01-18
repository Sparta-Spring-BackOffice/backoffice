package com.example.backoffice.jwt;

import com.example.backoffice.authentification.exception.AuthErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    // Java 객체 <-> Json 문자열 변환기
    // 응답 만들 때 객체 -> JSON
    // 요청 바디 파싱할 때 JSON -> 객체
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        // 응답 값에 구조 전달(JSON, UTF-8)
        response.setContentType("application/json;charset=UTF-8");

        JwtErrorResponse body = JwtErrorResponse.of(
                HttpStatus.FORBIDDEN,
                AuthErrorCode.ACCESS_DENIED,
                request.getRequestURI()
        );
        // jwtErrorResponse 객체를 JSON 문자열로 변환
        // Spring MVC Controller에서는 자동으로 해주지만
        // Filter, Handler 영역에서는 직접 해줘야 함 (SpringSecurity 영역)
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
