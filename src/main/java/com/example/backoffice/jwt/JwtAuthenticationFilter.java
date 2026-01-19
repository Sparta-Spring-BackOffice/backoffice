package com.example.backoffice.jwt;

import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.UnauthorizedException;
import com.example.backoffice.common.dto.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/admin/login") || path.equals("/admin/signup");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // 토큰 없으면 401 (인증 에러 발생)
        // 공통된 에러 응답 메세지 출력을 위해 별도로 구현
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            writeUnauthorized(request, response, AuthErrorCode.TOKEN_MISSING);
            return;
        }
        String token = authHeader.substring(7);

        try {
            // 토큰 서명검증, 유효기간 검증 성공 시 try문 실행, 실패시 catch문에서 예외 처리
            jwtUtil.validateOrThrow(token);

            // 검증이 끝난 토큰으로 authentication 새로 생성
            // JWT - 신분증, Authentication - 출입증, 매번 요청시 출입증 발급
            Authentication authentication = jwtUtil.getAuthenticationFromToken(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (UnauthorizedException e) {
            writeUnauthorized(request, response, e.getAuthErrorCode());
            return;
        } catch (Exception e) {
            writeUnauthorized(request, response, AuthErrorCode.TOKEN_INVALID);
            return;
        }
    }

    // response에 상태코드와 에러메세지 json을 첨부
    // 에러 응답메세지 공통 규격을 지키기 위해 별도의 메서드로 구현
    private void writeUnauthorized(HttpServletRequest request,
                                   HttpServletResponse response,
                                   AuthErrorCode code) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ErrorResponse body = ErrorResponse.of(
                HttpStatus.UNAUTHORIZED,
                code.getCode(),
                code.getMessage(),
                request.getRequestURI()
        );
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
