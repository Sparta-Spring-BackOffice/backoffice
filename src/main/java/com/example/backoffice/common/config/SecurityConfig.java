package com.example.backoffice.common.config;

import com.example.backoffice.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // JWT는 스테이트리스, CSRF 보호가 필요 없음
                .csrf(AbstractHttpConfigurer::disable)

                // 서버 세션 생성 X
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 접근 권한 설정, 로그인 및 회원가입은 JWT 인증 없이 허용
                        .requestMatchers("/admin/login", "/admin/signup")
                        .permitAll() // 앞에 선언한 앤드포인트에 대해 모든 보안 검사를 건너띔
                        // .permitAll()을 적용해도 필터 체인(예: JWT, CSRF 등)과 인증 단계는 그대로 유지되어,
                        // 권한 없는 접근 시 403(Forbidden) 또는 인증 오류가 발생할 수 있음

                        // 접근 권한 설정, 권한에 따른 앤드포인트 접근 제한
                        .requestMatchers("/admin/administrators/**")
                        .hasRole("SUPER_ADMIN")// 앞에 선언한 앤드포인트에 대해 해당 권한자만 접근 가능
                        // 동일한 앤드포인트가 HTTP 메서드로 구분 되어 있으면(POST, GET 등) HttpMethod 추가
                        .requestMatchers(HttpMethod.DELETE,"/admin/users/{userId}")
                        .hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/admin/products")
                        .hasAnyRole("SUPER_ADMIN", "OP_ADMIN")// hasAnyRole 은 여러명에 권한자 지정할 때 사용
                        .requestMatchers(HttpMethod.DELETE, "/admin/reviews/{reviewId}")
                        .hasAnyRole("SUPER_ADMIN", "OP_ADMIN")
                        .requestMatchers("/admin/dashboards/**")
                        .hasAnyRole("SUPER_ADMIN", "OP_ADMIN")
                        .anyRequest().authenticated() // 나머지는 JWT 필수
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}