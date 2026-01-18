package com.example.backoffice.jwt;

import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.UnauthorizedException;
import com.example.backoffice.common.dto.ErrorResponse;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

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

        // ✅ 토큰 없으면 401 (1번 방식)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            writeUnauthorized(request, response, AuthErrorCode.TOKEN_MISSING);
            return;
        }
        String token = authHeader.substring(7);
        try {
            // ✅ void 메서드: 성공하면 그냥 통과, 실패하면 예외 던짐
            jwtUtil.validateOrThrow(token);

            // ✅ Claims에서 id 꺼내기
            Claims claims = jwtUtil.getUserIdFromToken(token);
            Long id = claims.get("id", Long.class);
            String role = claims.get("role", String.class);

            // Spring Security 인가 판단은 반드시 GrantedAuthority 객체로만 판단
            // Authentication(id,null,"권한")으론 판단 안됨
            // "권한"을 Collection<? extends GrantedAuthority>으로 받아서 담아야 함
            // SimpleGrantedAuthority -> "권한"을 GrantedAuthority 객체로 변환
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

            // 검증이 끝난 토큰으로 authentication 새로 생성
            // JWT - 신분증, Authentication - 출입증, 매번 요청시 출입증 발급
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(id, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        } catch (UnauthorizedException e) {
            // ✅ UnauthorizedException에 errorCode getter 이름 맞춰서 사용
            writeUnauthorized(request, response, e.getAuthErrorCode());
            return;
        } catch (Exception e) {
            writeUnauthorized(request, response, AuthErrorCode.TOKEN_INVALID);
            return;
        }
    }

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
