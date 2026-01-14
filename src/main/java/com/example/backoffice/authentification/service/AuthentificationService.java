package com.example.backoffice.authentification.service;

import com.example.backoffice.admin.consts.DeclineReason;
import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.exception.AdminNotFoundException;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.authentification.consts.AuthStatus;
import com.example.backoffice.authentification.dto.LoginRequest;
import com.example.backoffice.authentification.dto.LoginResponse;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.LoginStatusException;
import com.example.backoffice.common.config.PasswordEncoder;
import com.example.backoffice.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class AuthentificationService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder pe;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Administrator admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.DUPLICATE_EMAIL)//에러 코드 추후 수정(이메일 불일치)
        );
        if(!pe.matches(request.getPassword(), admin.getPassword())){
            throw new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND);//에러 코드 추후 수정(비밀번호 불일치)
        }
        //AdminStatus
        switch (admin.getStatus()) {
            case ACTIVE -> { /* OK */ }
            case PENDING -> throw new LoginStatusException(AuthErrorCode.LOGIN_PENDING_ERROR);
            case DENIED -> {
                throw new LoginStatusException(AuthErrorCode.LOGIN_PENDING_ERROR);//사유 추가 해야함
            }
            case SUSPENDED -> throw new LoginStatusException(AuthErrorCode.LOGIN_SUSPENDED_ERROR);
            case NON_ACTIVE -> throw new LoginStatusException(AuthErrorCode.LOGIN_NON_ACTIVE_ERROR);
        }


        return new LoginResponse(
                admin.getId(),
                admin.getEmail(),
                admin.getRole(),
                AuthStatus.LOGIN_SUCCESS
        );
    }

    private String declineReasonMessage(DeclineReason reason) {
        return switch (reason) {
            case UNAUTHORIZED -> "권한이 승인 기준에 부합하지 않습니다.";
            case ADMIN_EXCESS -> "관리자 정원이 초과되었습니다.";
            case TIME_OUT -> "승인 처리 시간이 초과되었습니다.";
        };
    }
 }

