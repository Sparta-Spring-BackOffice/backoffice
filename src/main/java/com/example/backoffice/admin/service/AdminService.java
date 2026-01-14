package com.example.backoffice.admin.service;

import com.example.backoffice.admin.consts.AdminRole;
import com.example.backoffice.admin.consts.AdminStatus;
import com.example.backoffice.admin.dto.CreateAdminRequest;
import com.example.backoffice.admin.dto.CreateAdminResponse;
import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.exception.EmailDuplicationException;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.common.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backoffice.common.exception.ErrorCode.DUPLICATE_EMAIL;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    @Transactional
    public CreateAdminResponse create(CreateAdminRequest request) {
        //이메일 중복 확인
        boolean duplicate = adminRepository.existsByEmail(request.getEmail());
        if(duplicate) throw new EmailDuplicationException(DUPLICATE_EMAIL);
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
}
