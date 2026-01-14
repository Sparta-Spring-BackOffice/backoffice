package com.example.backoffice.authentification.controller;

import com.example.backoffice.authentification.dto.LoginRequest;
import com.example.backoffice.authentification.dto.LoginResponse;
import com.example.backoffice.authentification.dto.SessionAdmin;
import com.example.backoffice.authentification.service.AuthentificationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class AuthentificationController {
    private final AuthentificationService authentificationService;

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session,
            @SessionAttribute(name="loginUser", required = false) SessionAdmin loginUser
    ) {
        if(loginUser != null){
            throw new IllegalStateException("이미 로그인");//에러 코드 수정 예정
        }
        LoginResponse loginResponse = authentificationService.login(request);

        SessionAdmin sessionAdmin = new SessionAdmin(
                loginResponse.getId(),
                loginResponse.getEmail(),
                loginResponse.getRole());

        session.setAttribute("loginUser", sessionAdmin);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

}
