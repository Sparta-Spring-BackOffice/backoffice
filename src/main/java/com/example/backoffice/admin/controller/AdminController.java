package com.example.backoffice.admin.controller;

import com.example.backoffice.admin.dto.CreateAdminRequest;
import com.example.backoffice.admin.dto.CreateAdminResponse;
import com.example.backoffice.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/admin/signup")
    public ResponseEntity<CreateAdminResponse> signup (CreateAdminRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.create(request));
    }
}
