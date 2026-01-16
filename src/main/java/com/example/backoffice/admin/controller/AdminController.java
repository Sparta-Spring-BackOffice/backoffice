package com.example.backoffice.admin.controller;

import com.example.backoffice.admin.dto.*;
import com.example.backoffice.admin.service.AdminService;
import com.example.backoffice.authentification.dto.SessionAdmin;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.UnauthorizedException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    //관리자 가입신청(조건 : 비로그인)
    @PostMapping("/admin/signup")
    public ResponseEntity<CreateAdminResponse> signup (
            @Valid @RequestBody CreateAdminRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.create(request));
    }
    //내 프로필 조회(조건 : 로그인, 권한 수준 : 모든 관리자)
    @GetMapping("/admin/profile")
    public ResponseEntity<GetAdminProfileResponse> getAdminProfile(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAdminProfile(loginAdmin.getId()));
    }
    //내 정보 수정(조건 : 로그인, 권한 수준 : 모든 관리자)
    @PutMapping("/admin/profile/general")
    public ResponseEntity<UpdateAdminResponse> updateAdmin(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin,
            @Valid @RequestBody UpdateAdminRequest request
    ) {
        if (loginAdmin == null) {
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdmin(loginAdmin.getId(), request));
    }
    //내 패스워드 수정(조건 : 로그인, 권한 수준 : 모든 관리자)
    @PutMapping("/admin/profile/password")
    public ResponseEntity<UpdateAdminPasswordResponse> updateAdminPassword(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin,
            @Valid @RequestBody UpdateAdminPasswordRequest request
    ) {
        if (loginAdmin == null) {
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdminPassword(loginAdmin.getId(), request));
    }
    //관리자 목록 전체조회(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @GetMapping("/admin/administrators")
    public ResponseEntity<List<GetAdminResponse>> getAllAdmins(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status,
            @PageableDefault Pageable pageable,
            @RequestParam(defaultValue = "1") int page
    ) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        Pageable converted = PageRequest.of(
                page - 1,
                pageable.getPageSize(),
                pageable.getSort()
        );
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllAdmins(loginAdmin.getId(), keyword, role, status, converted));
    }
    //관리자 단건조회(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @GetMapping("/admin/administrators/{administratorId}")
    public ResponseEntity<GetOneAdminResponse> getOneAdmin(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin,
            @PathVariable Long administratorId) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getOneAdmin(loginAdmin.getId(), administratorId));
    }
    //관리자 거부(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PutMapping("/admin/administrators/{administratorId}/denial")
    public ResponseEntity<Void> denyAdmin(
            @Valid @RequestBody RejectAdminRequest request,
            @PathVariable Long administratorId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin
    ) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        adminService.denyAdmin(request, loginAdmin.getId(), administratorId );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //관리자 활성화(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PutMapping("/admin/administrators/{administratorId}/activation")
    public ResponseEntity<Void> activateAdmin(
            @PathVariable Long administratorId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin
    ) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        adminService.activateAdmin(loginAdmin.getId(), administratorId );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //관리자 권한정지(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PutMapping("/admin/administrators/{administratorId}/suspension")
    public ResponseEntity<Void> suspendAdmin(
            @PathVariable Long administratorId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin
    ) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }

        adminService.suspendAdmin(loginAdmin.getId(), administratorId );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //관리자 비활성화(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PutMapping("/admin/administrators/{administratorId}/deactivation")
    public ResponseEntity<Void> deactivateAdmin(
            @PathVariable Long administratorId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin
    ) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        adminService.deactivateAdmin(loginAdmin.getId(), administratorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //관리자 삭제(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @DeleteMapping("/admin/administrators/{administratorId}/deletion")
    public ResponseEntity<Void> deleteAdmin(
            @PathVariable Long administratorId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin
    ) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        adminService.deleteAdmin(loginAdmin.getId(), administratorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //관리자 정보 수정(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PutMapping("/admin/administrators/{administratorId}")
    public ResponseEntity<UpdateAdminResponse> updateBySuperAdmin(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin,
            @PathVariable Long administratorId,
            @Valid @RequestBody UpdateAdminRequest request
    ) {
        if (loginAdmin == null) {
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateBySuperAdmin(loginAdmin.getId(), administratorId, request));
    }

//  관리자 역할 변경
    @PutMapping("/admin/administrators/role/{administratorId}")
    public ResponseEntity<UpdateAdminRoleResponse> updateRole(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin,
            @PathVariable Long administratorId,
            @Valid @RequestBody UpdateAdminRoleRequest request){
    return ResponseEntity.status(HttpStatus.OK).body(adminService.updateRole(loginAdmin.getId(), administratorId, request));
    }
}
