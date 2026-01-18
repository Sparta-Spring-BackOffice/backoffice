package com.example.backoffice.admin.service;

import com.example.backoffice.admin.dto.*;
import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.consts.AdminRole;
import com.example.backoffice.admin.consts.AdminStatus;
import com.example.backoffice.admin.exception.AdminNotFoundException;
import com.example.backoffice.admin.exception.EmailDuplicationException;
import com.example.backoffice.admin.exception.InsufficientRoleException;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.UnauthorizedException;
import com.example.backoffice.common.responsecode.ErrorCode;
import com.example.backoffice.common.exception.InvalidRequestException;
import com.example.backoffice.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public List<GetAdminResponse> getAllAdmins(Long loginId, String keyword, String role, String status, Pageable pageable) {
        //슈퍼 관리자인지 검사
        checkSuperAdmin(loginId);

        Page<Administrator> findAdmins;

        if(keyword == null || keyword.isEmpty()){ //keyword에 대한 null 체크
            keyword = null;
        }

        if (keyword != null && keyword.contains("@")) {
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

    @Transactional(readOnly = true)
    public GetOneAdminResponse getOneAdmin(Long loginId, Long administratorId) {
        //슈퍼 관리자인지 검사
        checkSuperAdmin(loginId);
        Administrator administrator = adminRepository.findById(administratorId).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );

        return new GetOneAdminResponse(
                administrator.getId(),
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

    @Transactional
    public CreateAdminResponse create(CreateAdminRequest request) {
        //이메일 중복 확인
        boolean duplicate = adminRepository.existsByEmail(request.getEmail());
        if(duplicate) throw new EmailDuplicationException(ErrorCode.DUPLICATE_EMAIL);

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
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhone(),
                admin.getRole().getRoleName(),
                admin.getStatus().getStatusName(),
                admin.getCreatedAt()
        );
    }

    @Transactional
    public UpdateAdminResponse updateAdmin(Long loginId, UpdateAdminRequest request) {

        //존재하는 관리자인지 검사
        Administrator administrator = findAndGet(loginId);
        //값 업데이트
        return updateResponse(administrator, request);
    }

    @Transactional
    public void denyAdmin(RejectAdminRequest request, Long loginId, Long targetId) {
        //슈퍼 관리자인지 검사
        checkSuperAdmin(loginId);
        //존재하는 관리자인지 검사
        Administrator targetAdmin = findAndGet(targetId);
        //거절
        targetAdmin.deny(request.getDeclineReason());
    }

    @Transactional
    public void activateAdmin(Long loginId, Long targetId) {
        //슈퍼 관리자인지 검사
        checkSuperAdmin(loginId);
        //존재하는 관리자인지 검사
        Administrator targetAdmin = findAndGet(targetId);
        //활성화
        targetAdmin.activate();
    }

    @Transactional
    public GetAdminProfileResponse getAdminProfile(Long loginId) {
        Administrator findAdmin = findAndGet(loginId);

        return new GetAdminProfileResponse(
                findAdmin.getId(),
                findAdmin.getName(),
                findAdmin.getEmail(),
                findAdmin.getPhone()
        );
    }

    @Transactional
    public UpdateAdminPasswordResponse updateAdminPassword(Long loginId, UpdateAdminPasswordRequest request) {
        Administrator administrator = findAndGet(loginId);

        if (!passwordEncoder.matches(request.getCurrentPassword(), administrator.getPassword())) {
            throw new UnauthorizedException(AuthErrorCode.PASSWORD_MISMATCH_ERROR);
        }

        if (!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
            throw new InvalidRequestException(ErrorCode.PASSWORD_CONFIRM_MISMATCH_ERROR);
        }

        administrator.updatePassword(passwordEncoder.encode(request.getNewPassword()));

        adminRepository.flush();

        return new UpdateAdminPasswordResponse(
                administrator.getId(),
                administrator.getName(),
                administrator.getEmail(),
                administrator.getPhone(),
                administrator.getRole().getRoleName(),
                administrator.getStatus().getStatusName(),
                administrator.getCreatedAt(),
                administrator.getModifiedAt()
        );
    }

    //activateAdmin, denyAdmin과 동일
    @Transactional
    public void suspendAdmin(Long loginId, Long targetId) {
        //슈퍼 관리자인지 검사
        checkSuperAdmin(loginId);
        //존재하는 관리자인지 검사
        Administrator targetAdmin = findAndGet(targetId);
        //상태 변경
        targetAdmin.suspend();
    }

    @Transactional
    public void deactivateAdmin(Long loginId, Long targetId) {
        //슈퍼 관리자인지 검사
        checkSuperAdmin(loginId);
        //존재하는 관리자인지 검사
        Administrator targetAdmin = findAndGet(targetId);
        //상태 변경
        targetAdmin.deactivate();
    }

    @Transactional
    public void deleteAdmin(Long loginId, Long administratorId) {
        //슈퍼 관리자인지 검사
        checkSuperAdmin(loginId);
        //존재하는 관리자인지만 검사
        boolean existence = adminRepository.existsById(administratorId);
        if(!existence) throw new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND);
        //상태 변경
        adminRepository.deleteById(administratorId);
    }

    @Transactional
    public UpdateAdminResponse updateBySuperAdmin(Long loginId, Long targetId, UpdateAdminRequest request) {
        //슈퍼 관리자인지 검사
        checkSuperAdmin(loginId);
        //존재하는 관리자인지 검사
        Administrator administrator = findAndGet(targetId);
        //업데이트
        return updateResponse(administrator, request);
    }
    //(공통기능)슈퍼 관리자인지 검사
    private void checkSuperAdmin(Long loginId) {
        Administrator loginAdmin = adminRepository.findById(loginId).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );
        if (loginAdmin.getRole() != AdminRole.SUPER_ADMIN) {
            throw new InsufficientRoleException(ErrorCode.INSUFFICIENT_ADMIN_ROLE);
        }
    }

    //(공통기능)존재하는 관리자인지 검사
    private Administrator findAndGet(Long targetId) {
        Administrator targetAdmin = adminRepository.findById(targetId).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );
        return targetAdmin;
    }

    //(공통기능)관리자 정보 업데이트
    private UpdateAdminResponse updateResponse(Administrator administrator, UpdateAdminRequest request){
        //업데이트
        administrator.updateGeneral(request.getName(), request.getEmail(), request.getPhone());

        adminRepository.flush();

        return new UpdateAdminResponse(
                administrator.getId(),
                administrator.getName(),
                administrator.getEmail(),
                administrator.getPhone(),
                administrator.getRole().getRoleName(),
                administrator.getStatus().getStatusName(),
                administrator.getCreatedAt(),
                administrator.getModifiedAt()
        );
    }

    @Transactional
    public UpdateAdminRoleResponse updateRole(Long loginId, Long targetId, @Valid UpdateAdminRoleRequest request) {
        //슈퍼 관리자인지 검사
        checkSuperAdmin(loginId);
        //존재하는 관리자인지 검사
        Administrator administrator = findAndGet(targetId);
        //업데이트
        administrator.updateRole(request.getRole());
        adminRepository.flush();
        return new UpdateAdminRoleResponse(
                administrator.getId(),
                administrator.getName(),
                administrator.getRole()
        );
    }
}
