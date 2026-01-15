package com.example.backoffice.admin.controller;

import com.example.backoffice.admin.dto.*;
import com.example.backoffice.admin.service.AdminService;
import com.example.backoffice.authentification.dto.SessionAdmin;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.NotLoginException;
import com.example.backoffice.common.exception.ErrorCode;
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
            throw new NotLoginException(AuthErrorCode.NOT_LOGIN);
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
            throw new NotLoginException(AuthErrorCode.NOT_LOGIN);
        }

        adminService.approveAdmin(loginAdmin.getId(), administratorId );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/admin/profile")
    public ResponseEntity<GetAdminProfileResponse> getAdminProfile(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin) {
        if(loginAdmin == null){
            throw new NotLoginException(AuthErrorCode.NOT_LOGIN);
        }
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAdminProfile(loginAdmin.getId()));
    }
}
