package com.example.backoffice.admin.service;

import com.example.backoffice.admin.dto.*;
import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.consts.AdminRole;
import com.example.backoffice.admin.consts.AdminStatus;
import com.example.backoffice.admin.exception.AdminNotFoundException;
import com.example.backoffice.admin.exception.EmailDuplicationException;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.common.config.PasswordEncoder;
import com.example.backoffice.common.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public List<GetAdminResponse> getAllAdmins(String keyword, String role, String status, Pageable pageable) {

        Page<Administrator> findAdmins;

        if (keyword.contains("@")) {
            findAdmins = adminRepository.findByEmailKeyword(keyword, role, status, pageable);
        } else {
            findAdmins = adminRepository.findByNameKeyword(keyword, role, status, pageable);
        }

        return findAdmins.stream()
                .map(administrator -> new GetAdminResponse(
                        administrator.getId(),
                        administrator.getName(),
                        administrator.getEmail(),
                        administrator.getPhone(),
                        administrator.getRole().getRoleName(),
                        administrator.getStatus().getStatusName(),
                        administrator.getCreatedAt(),
                        administrator.getModifiedAt(),
                        findAdmins.getNumber(),
                        findAdmins.getSize(),
                        findAdmins.getTotalElements(),
                        findAdmins.getTotalPages()
                )).toList();
    }

    @Transactional
    public CreateAdminResponse create(CreateAdminRequest request) {
        //이메일 중복 확인
        boolean duplicate = adminRepository.existsByEmail(request.getEmail());
        if(duplicate) throw new EmailDuplicationException(ErrorCode.DUPLICATE_EMAIL);
        //비밀번호 암호화 및 저장
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        Administrator admin = new Administrator(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getPhone(),
                AdminRole.getRole(request.getRole()),
                AdminStatus.PENDING
        );
        adminRepository.save(admin);
        return new CreateAdminResponse(
          "관리자 계정이 성공적으로 생성되었습니다.",
                admin.getName(),
                admin.getEmail(),
                admin.getPhone(),
                admin.getRole().getRoleName(),
                admin.getStatus().getStatusName(),
                admin.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public GetOneAdminResponse getOneAdmin(Long administratorId) {
        Administrator administrator = adminRepository.findById(administratorId).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );

        return new GetOneAdminResponse(
                administrator.getName(),
                administrator.getEmail(),
                administrator.getPhone(),
                administrator.getRole().getRoleName(),
                administrator.getStatus().getStatusName(),
                administrator.getCreatedAt(),
                administrator.getModifiedAt(),
                administrator.getApprovedAt()
        );
    }

    public UpdateAdminResponse updateAdmin(Long administratorId, UpdateAdminRequest request) {
        Administrator administrator = adminRepository.findById(administratorId).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );

        administrator.update(request.getName(), request.getEmail(), request.getPhone());

        return new UpdateAdminResponse(
                administrator.getName(),
                administrator.getEmail(),
                administrator.getPhone(),
                administrator.getRole().getRoleName(),
                administrator.getStatus().getStatusName(),
                administrator.getCreatedAt(),
                administrator.getModifiedAt()
        );
    }
}
