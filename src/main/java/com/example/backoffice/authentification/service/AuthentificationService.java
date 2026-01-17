package com.example.backoffice.authentification.service;

import com.example.backoffice.admin.consts.DeclineReason;
import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.authentification.consts.AuthStatus;
import com.example.backoffice.authentification.dto.LoginRequest;
import com.example.backoffice.authentification.dto.LoginResponse;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.LoginDeniedException;
import com.example.backoffice.authentification.exception.LoginFailException;
import com.example.backoffice.common.config.PasswordEncoder;
import com.example.backoffice.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class AuthentificationService {
    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder pe;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Administrator admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new LoginFailException(AuthErrorCode.LOGIN_ERROR)
        );
        if(!pe.matches(request.getPassword(), admin.getPassword())){
            throw new LoginFailException(AuthErrorCode.LOGIN_ERROR);
        }
        //AdminStatus
        switch (admin.getStatus()) {
            case ACTIVE -> { /* OK */ }
            case PENDING -> throw new LoginFailException(AuthErrorCode.LOGIN_PENDING_ERROR);
            case DENIED -> {
                throw new LoginDeniedException(AuthErrorCode.LOGIN_DENIED_ERROR, admin.getDeclineFor());
            }
            case SUSPENDED -> throw new LoginFailException(AuthErrorCode.LOGIN_SUSPENDED_ERROR);
            case NON_ACTIVE -> throw new LoginFailException(AuthErrorCode.LOGIN_NON_ACTIVE_ERROR);
        }

        String token = jwtUtil.generateToken(admin.getId(), admin.getEmail(), admin.getRole());

        return new LoginResponse(
                admin.getId(),
                admin.getEmail(),
                admin.getRole(),
                AuthStatus.LOGIN_SUCCESS,
                token
        );
    }
 }

