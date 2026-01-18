package com.example.backoffice.authentification.service;

import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.authentification.consts.AuthStatus;
import com.example.backoffice.authentification.dto.LoginRequest;
import com.example.backoffice.authentification.dto.LoginResponse;
import com.example.backoffice.jwt.AdminDetails;
import com.example.backoffice.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthentificationService {
    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder pe;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public LoginResponse login(LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        // 해당 authentication 에는 AdminDetails/null/AdminDetails.getAuthorities 가 들어가있음
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String token = jwtUtil.generateToken(authentication);

        AdminDetails adminDetails = (AdminDetails) authentication.getPrincipal();
        Administrator administrator = adminDetails.getAdministrator();

        return new LoginResponse(
                administrator.getId(),
                administrator.getEmail(),
                administrator.getRole(),
                AuthStatus.LOGIN_SUCCESS,
                token
        );
    }
 }

