package com.example.backoffice.admin.dto;

import com.example.backoffice.admin.consts.AdminRole;
import lombok.Getter;

@Getter
public class UpdateAdminRoleResponse {
    private final String adminName;
    private final AdminRole newRole;

    public UpdateAdminRoleResponse(String adminName, AdminRole newRole) {
        this.adminName = adminName;
        this.newRole = newRole;
    }
}
