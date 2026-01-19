package com.example.backoffice.admin.controller;

import com.example.backoffice.admin.dto.*;
import com.example.backoffice.admin.service.AdminService;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.UnauthorizedException;
import com.example.backoffice.common.dto.SuccessResponse;
import com.example.backoffice.common.responsecode.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.example.backoffice.common.responsecode.ResponseProcess.responseWithBody;
import static com.example.backoffice.common.responsecode.ResponseProcess.responseWithBuild;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    //관리자 가입신청(조건 : 비로그인)
    @PostMapping("/admin/signup")
    public ResponseEntity<SuccessResponse<CreateAdminResponse>> signup (
            @Valid @RequestBody CreateAdminRequest request){
        return responseWithBody(SuccessCode.CREATE_SUCCESS, adminService.create(request));
    }
    //내 프로필 조회(조건 : 로그인, 권한 수준 : 모든 관리자)
    @GetMapping("/admin/profile")
    public ResponseEntity<SuccessResponse<GetAdminProfileResponse>> getAdminProfile(Authentication auth) {
        if(auth == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return responseWithBody(SuccessCode.READ_SUCCESS, adminService.getAdminProfile((Long)auth.getPrincipal()));
    }
    //내 정보 수정(조건 : 로그인, 권한 수준 : 모든 관리자)
    @PutMapping("/admin/profile/general")
    public ResponseEntity<SuccessResponse<UpdateAdminResponse>> updateAdmin(
            Authentication auth,
            @Valid @RequestBody UpdateAdminRequest request
    ){
        if (auth == null) {
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return responseWithBody(SuccessCode.UPDATE_SUCCESS,adminService.updateAdmin((Long)auth.getPrincipal(), request));
    }
    //내 패스워드 수정(조건 : 로그인, 권한 수준 : 모든 관리자)
    @PutMapping("/admin/profile/password")
    public ResponseEntity<SuccessResponse<UpdateAdminPasswordResponse>> updateAdminPassword(
            Authentication auth,
            @Valid @RequestBody UpdateAdminPasswordRequest request
    ){
        return responseWithBody(SuccessCode.UPDATE_SUCCESS, adminService.updateAdminPassword((Long) auth.getPrincipal(), request));
    }
    //관리자 목록 전체조회(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/admin/administrators")
    public ResponseEntity<SuccessResponse<List<GetAdminResponse>>> getAllAdmins(
            Authentication auth,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status,
            @PageableDefault Pageable pageable,
            @RequestParam(defaultValue = "1") int page
    ){
        if(auth == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        Pageable converted = PageRequest.of(
                page - 1,
                pageable.getPageSize(),
                pageable.getSort()
        );
        return responseWithBody(SuccessCode.READ_SUCCESS, adminService.getAllAdmins((Long)auth.getPrincipal(), keyword, role, status, converted));
    }
    //관리자 단건조회(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/admin/administrators/{administratorId}")
    public ResponseEntity<SuccessResponse<GetOneAdminResponse>> getOneAdmin(
            Authentication auth,
            @PathVariable Long administratorId) {
        if(auth == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return responseWithBody(SuccessCode.READ_SUCCESS, adminService.getOneAdmin((Long)auth.getPrincipal(), administratorId));
    }
    //관리자 거부(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/admin/administrators/{administratorId}/denial")
    public ResponseEntity<SuccessResponse<Void>> denyAdmin(
            @Valid @RequestBody RejectAdminRequest request,
            @PathVariable Long administratorId,
            Authentication auth
    ){
        if(auth == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        adminService.denyAdmin(request, (Long) auth.getPrincipal(), administratorId );
        return responseWithBuild(SuccessCode.UPDATE_SUCCESS, null);
    }
    //관리자 활성화(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/admin/administrators/{administratorId}/activation")
    public ResponseEntity<SuccessResponse<Void>> activateAdmin(
            @PathVariable Long administratorId,
            Authentication auth
    ){
        if(auth == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        adminService.activateAdmin((Long)auth.getPrincipal(), administratorId );
        return responseWithBuild(SuccessCode.UPDATE_SUCCESS, null);
    }
    //관리자 권한정지(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/admin/administrators/{administratorId}/suspension")
    public ResponseEntity<SuccessResponse<Void>> suspendAdmin(
            @PathVariable Long administratorId,
            Authentication auth
    ){
        if(auth == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        adminService.suspendAdmin((Long)auth.getPrincipal(), administratorId );
        return responseWithBuild(SuccessCode.UPDATE_SUCCESS, null);
    }
    //관리자 비활성화(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/admin/administrators/{administratorId}/deactivation")
    public ResponseEntity<SuccessResponse<Void>> deactivateAdmin(
            @PathVariable Long administratorId,
            Authentication auth
    ){
        if(auth == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        adminService.deactivateAdmin((Long)auth.getPrincipal(), administratorId);
        return responseWithBuild(SuccessCode.UPDATE_SUCCESS, null);
    }
    //관리자 삭제(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/admin/administrators/{administratorId}/deletion")
    public ResponseEntity<SuccessResponse<Void>> deleteAdmin(
            @PathVariable Long administratorId,
            Authentication auth
    ){
        if(auth == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        adminService.deleteAdmin((Long)auth.getPrincipal(), administratorId);
        return responseWithBuild(SuccessCode.DELETE_SUCCESS, null);
    }
    //관리자 정보 수정(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/admin/administrators/{administratorId}")
    public ResponseEntity<SuccessResponse<UpdateAdminResponse>> updateBySuperAdmin(
            Authentication auth,
            @PathVariable Long administratorId,
            @Valid @RequestBody UpdateAdminRequest request
    ){
        if (auth == null) {
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return responseWithBody(SuccessCode.UPDATE_SUCCESS, adminService.updateBySuperAdmin((Long)auth.getPrincipal(), administratorId, request));
    }

//  관리자 역할 변경(조건 : 로그인, 권한 수준 : 슈퍼 관리자)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/admin/administrators/role/{administratorId}")
    public ResponseEntity<SuccessResponse<UpdateAdminRoleResponse>> updateRole(
            Authentication auth,
            @PathVariable Long administratorId,
            @Valid @RequestBody UpdateAdminRoleRequest request){
        if (auth == null) {
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return responseWithBody(SuccessCode.UPDATE_SUCCESS, adminService.updateRole((Long)auth.getPrincipal(), administratorId, request));
    }
}
