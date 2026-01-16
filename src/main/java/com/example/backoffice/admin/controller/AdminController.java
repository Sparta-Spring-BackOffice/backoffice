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
    //관리자 목록 조회
    @GetMapping("/admin/administrators")
    public ResponseEntity<List<GetAdminResponse>> getAllAdmins(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status,
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
    //관리자 신규등록
    @PostMapping("/admin/signup")
    public ResponseEntity<CreateAdminResponse> signup (
            @Valid @RequestBody CreateAdminRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.create(request));
    }
    //관리자 단건조회
    @GetMapping("/admin/administrators/{administratorId}")
    public ResponseEntity<GetOneAdminResponse> getOneAdmin(@PathVariable Long administratorId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getOneAdmin(administratorId));
    }
    //
    @PutMapping("/admin/administrators/{administratorId}")
    public ResponseEntity<UpdateAdminResponse> updateAdmin(
            @PathVariable Long administratorId,
            @Valid @RequestBody UpdateAdminRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdmin(administratorId, request));
    }
    //동사가 들어가면 안 된대서 명사로 바꿈
    @PutMapping("/admin/administrators/{administratorId}/denial")
    //denyAdmin이 더 직관적인 이름 같아서 바꿈
    public ResponseEntity<Void> denyAdmin(
            @Valid @RequestBody RejectAdminRequest request,
            @PathVariable Long administratorId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin
    ) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
    //denyAdmin이 더 직관적인 이름 같아서 바꿈
        adminService.denyAdmin(request, loginAdmin.getId(), administratorId );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //직관적
    @PutMapping("/admin/administrators/{administratorId}/activation")
    public ResponseEntity<Void> activateAdmin(
            @PathVariable Long administratorId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin
    ) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        //직관적이라 바꿈
        adminService.activateAdmin(loginAdmin.getId(), administratorId );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //관리자 프로필 조회
    @GetMapping("/admin/profile")
    public ResponseEntity<GetAdminProfileResponse> getAdminProfile(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAdminProfile(loginAdmin.getId()));
    }
    //관리자 패스워드 수정
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
    //관리자 개인정보 수정
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
    //deny approve와 같은 형태로 만들어 통일성을 주고자 함. 구조는 requestBody가 없다는 점에서 approve와 완전히 동일
    //관리자 상태 변경 - suspend
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
    //관리자 상태 변경 - deactivate
    @PutMapping("/admin/administrators/{administratorId}/deactivation")
    public ResponseEntity<Void> deactivateAdmin(
            @PathVariable Long administratorId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin
    ) {
        if(loginAdmin == null){
            throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
        }

        adminService.deactivateAdmin(loginAdmin.getId(), administratorId );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //아직 코드 구현 전
    //관리자 역할 변경
//    @PutMapping("/admin/administrators/role/{administratorId}")
//    public ResponseEntity<UpdateAdminRoleResponse> updateAdminRole(
//            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin,
//            @PathVariable Long administratorId,
//            @Valid @RequestBody updateAdminRoleRequest request){
//    return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdminRole(loginAdmin.getId(), administratorId, request));
//    }
//    //관리자 삭제
//    @DeleteMapping("/admin/administrators/delete/{administratorId}")
//    public ResponseEntity<Void> deleteAdmin(
//            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin,
//            @PathVariable Long administratorId
//    ){
//    adminService.delete(loginAdmin.getId(), administratorId);
//    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
}
