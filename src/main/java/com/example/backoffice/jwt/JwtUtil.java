package com.example.backoffice.jwt;

import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.exception.AdminNotFoundException;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.UnauthorizedException;
import com.example.backoffice.common.responsecode.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

//    private final AdminRepository adminRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration; // 밀리초 단위 (예: 3600000 = 1시간)

    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 생성
    public String generateToken(Authentication authentication) {

        // List<GrantedAuthority> 타입의 리스트(authentication.getAuthorities()) 를 스트림으로 변환
        // 스트림의 각 GrantedAuthority 타입 객체에 대해 getAuthority를 사용하여 "ROLE_OP_ADMIN" 과 같이 string 타입의 스트림으로 변환
        // 해당 string 타입의 스트림을 ,를 이용하여 하나의 String 으로 묶음
        //  ex. role = "ROLE_SUPER_ADMIN,ROLE_OP_ADMIN"
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        AdminDetails adminDetails = (AdminDetails) authentication.getPrincipal();
        Administrator admin = adminDetails.getAdministrator();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(adminDetails.getUsername())
                .claim("id", admin.getId())
                .claim("email", adminDetails.getUsername())
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateOrThrow(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(AuthErrorCode.TOKEN_EXPIRED);
        } catch (SignatureException e) {
            throw new UnauthorizedException(AuthErrorCode.TOKEN_INVALID_SIGNATURE);
        } catch (io.jsonwebtoken.security.SecurityException e) {
            throw new UnauthorizedException(AuthErrorCode.TOKEN_INVALID_SIGNATURE);
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException(AuthErrorCode.TOKEN_MALFORMED);
        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException(AuthErrorCode.TOKEN_UNSUPPORTED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException(AuthErrorCode.TOKEN_INVALID);
        }
    }

    // JWT에서 사용자 claims 추출
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰을 사용하여 authentication 생성
    public Authentication getAuthenticationFromToken(String token) {
        Claims claims = getClaimsFromToken(token);

        Long id = claims.get("id", Long.class);
        String role = claims.get("role", String.class);

        // get("role")로 꺼낸 값은 object라 toString() 필요
        // "ROLE_SUPER_ADMIN,ROLE_OP_ADMIN" 를 "," 단위로 잘라서 [ROLE_SUPER_ADMIN, ROLE_OP_ADMIN] 이런 배열로 만듦
        // Arrays.stream 를 사용하여 해당 배열을 스트림으로 변환
        // 해당 스트림의 문자열을 SimpleGrantedAuthority 타입객체로 만듦
        // 이를 리스트로 바꿔서 authentication 에 저장
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("role").toString().split(","))
                .map(roleName -> new SimpleGrantedAuthority(roleName))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(id, null, authorities);
    }
}