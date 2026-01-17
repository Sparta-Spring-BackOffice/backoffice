package com.example.backoffice.authentification.controller;

import com.example.backoffice.authentification.dto.LoginRequest;
import com.example.backoffice.authentification.dto.LoginResponse;
import com.example.backoffice.authentification.dto.SessionAdmin;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.LoginFailException;
import com.example.backoffice.authentification.exception.UnauthorizedException;
import com.example.backoffice.authentification.service.AuthentificationService;
import com.example.backoffice.common.config.PasswordEncoder;
import com.example.backoffice.common.dto.SuccessResponse;
import com.example.backoffice.common.responsecode.ResponseProcess;
import com.example.backoffice.common.responsecode.SuccessCode;
import com.example.backoffice.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class AuthentificationController {
    private final AuthentificationService authentificationService;
    private final JwtUtil jwtUtil; //Jwt 유틸리티
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/admin/login")
    public ResponseEntity<SuccessResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request,
            Authentication auth
    ) {
        if(auth != null){
            throw new LoginFailException(AuthErrorCode.ALREADY_LOGIN);
        }

        LoginResponse loginResponse = authentificationService.login(request);

        return ResponseProcess.responseWithBody(SuccessCode.LOGIN_SUCCESS, loginResponse);
    }

    @PostMapping("/admin/logout")
    public ResponseEntity<SuccessResponse<Void>> logout(Authentication auth){

        if(auth.getPrincipal() == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return ResponseProcess.responseWithBuild(SuccessCode.LOGOUT_SUCCESS, null);
    }
}
