package com.example.backoffice.authentification.service;

import com.example.backoffice.admin.consts.DeclineReason;
import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.authentification.consts.AuthStatus;
import com.example.backoffice.authentification.dto.LoginRequest;
import com.example.backoffice.authentification.dto.LoginResponse;
import com.example.backoffice.authentification.dto.SessionAdmin;
import com.example.backoffice.authentification.exception.LoginStatusException;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class AuthentificationService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder pe;

    @Transactional
    public LoginResponse login(LoginRequest request, HttpSession session) {
        Administrator admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 이메일")//에러 코드 추후 수정
        );
        if(!pe.matches(request.getPassword(), admin.getPassword())){
            throw new IllegalStateException("비밀번호가 틀립니다.");//에러 코드 추후 수정
        }
        //AdminStatus
        switch (admin.getStatus()) {
            case ACTIVE -> { /* OK */ }
            case PENDING -> throw new LoginStatusException("계정 승인 대기 중입니다.");//에러 코드 추후 수정
            case DENIED -> {
                throw new LoginStatusException(
                        "계정 신청이 거부되었습니다. 사유: " + declineReasonMessage(admin.getDeclineFor())
                );
            }
            case SUSPENDED -> throw new LoginStatusException("정지된 계정입니다.");//에러 코드 추후 수정
            case NON_ACTIVE -> throw new LoginStatusException("비활성화된 계정입니다.");//에러 코드 추후 수정
        }

        SessionAdmin sessionAdmin = new SessionAdmin(admin.getId(), admin.getEmail(), admin.getRole());
        session.setAttribute("loginUser", sessionAdmin);

        return new LoginResponse(
                admin.getId(),
                admin.getName(),
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

