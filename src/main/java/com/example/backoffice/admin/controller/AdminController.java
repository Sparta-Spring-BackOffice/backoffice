package com.example.backoffice.admin.controller;

import com.example.backoffice.admin.dto.GetAdminResponse;
import com.example.backoffice.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllAdmins(keyword,role,status,converted));
    }
}
