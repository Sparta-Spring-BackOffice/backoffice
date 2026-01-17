package com.example.backoffice.security.jwt;

import com.example.backoffice.admin.consts.AdminRole;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration; // 밀리초 단위 (예: 3600000 = 1시간)

    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 생성
    public String generateToken(Long Id, String userEmail, AdminRole role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(userEmail)
                .claim("id", Id)
                .claim("email", userEmail)
                .claim("role", role.toAuthority().getAuthority())
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