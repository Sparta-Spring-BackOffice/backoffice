package com.example.backoffice.admin.controller;

import com.example.backoffice.admin.dto.*;
import com.example.backoffice.admin.service.AdminService;
import com.example.backoffice.authentification.dto.SessionAdmin;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.UnauthorizedException;
import com.example.backoffice.authentification.exception.NotLoginException;
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

    @GetMapping("/admin/administrators")
    public ResponseEntity<List<GetAdminResponse>> getAllAdmins(
            @RequestParam String keyword,
            @RequestParam String role,
            @RequestParam String status,
            @PageableDefault Pageable pageable,
            @RequestParam(defaultValue = "1") int page
    ) {
        Pageable converted = PageRequest.of(
                page - 1,
                pageable.getPageSize(),
                pageable.getSort()
        );
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllAdmins(keyword, role, status, converted));
    }

    @PostMapping("/admin/signup")
    public ResponseEntity<CreateAdminResponse> signup (
            @Valid @RequestBody CreateAdminRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.create(request));
    }

    @GetMapping("/admin/administrators/{administratorId}")
    public ResponseEntity<GetOneAdminResponse> getOneAdmin(@PathVariable Long administratorId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getOneAdmin(administratorId));
    }

    @PutMapping("/admin/administrators/{administratorId}")
    public ResponseEntity<UpdateAdminResponse> updateAdmin(
            @PathVariable Long administratorId,
            @Valid @RequestBody UpdateAdminRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdmin(administratorId, request));
    }

    @PutMapping("/admin/administrators/{administratorId}/reject")
    public ResponseEntity<Void> rejectAdmin(
            @Valid @RequestBody RejectAdminRequest request,
            @PathVariable Long administratorId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin
    ) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }

        adminService.rejectAdmin(request, loginAdmin.getId(), administratorId );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/admin/administrators/{administratorId}/approve")
    public ResponseEntity<Void> approveAdmin(
            @PathVariable Long administratorId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin
    ) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }

        adminService.approveAdmin(loginAdmin.getId(), administratorId );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/admin/profile")
    public ResponseEntity<GetAdminProfileResponse> getAdminProfile(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAdminProfile(loginAdmin.getId()));
    }

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

    @PutMapping("/admin/profile")
    public ResponseEntity<UpdateAdminResponse> updateAdmin(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin,
            @Valid @RequestBody UpdateAdminRequest request
    ) {
        if (loginAdmin == null) {
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }

        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdmin(loginAdmin.getId(), request));
    }

    //관리자 상태 변경
    @PutMapping("/admin/administrators/status/{administratorId}")
    public ResponseEntity<UpdateAdminStatusResponse> updateAdminStatus(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin,
            @PathVariable Long administratorId,
            @Valid @RequestBody updateAdminStatusRequest request)
    {
    return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdminStatus(loginAdmin.getId(), administratorId, request));
    }
    //관리자 역할 변경
    @PutMapping("/admin/administrators/role/{administratorId}")
    public ResponseEntity<UpdateAdminRoleResponse> updateAdminRole(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin,
            @PathVariable Long administratorId,
            @Valid @RequestBody updateAdminRoleRequest request){
    return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdminRole(loginAdmin.getId(), administratorId, request));
    }
    //관리자 삭제
    @DeleteMapping("/admin/administrators/delete/{administratorId}")
    public ResponseEntity<Void> deleteAdmin(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin,
            @PathVariable Long administratorId
    ){
    adminService.delete(loginAdmin.getId(), administratorId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
