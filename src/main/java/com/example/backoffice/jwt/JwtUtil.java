package com.example.backoffice.jwt;

import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.exception.AdminNotFoundException;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.UnauthorizedException;
import com.example.backoffice.common.responsecode.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtUtil {

    private final AdminRepository adminRepository;
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration; // 밀리초 단위 (예: 3600000 = 1시간)

    public JwtUtil(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 생성
    public String generateToken(Authentication authentication) {

        Long id = (Long) authentication.getPrincipal();
        // authentication에 저장된 권한 목록을 authorities에 불러옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // 권한이 여러개일 경우 List로 받아와야 함 (현재는 1개라 첫번째 값만 찾음)
        String role = authorities.iterator().next().getAuthority();

        Administrator admin = adminRepository.findById(id).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );
        String userEmail = admin.getEmail();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(userEmail)
                .claim("id", id)
                .claim("email", userEmail)
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

    // JWT에서 사용자 ID 추출
    public Claims getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}