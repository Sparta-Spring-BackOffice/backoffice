package com.example.backoffice.admin.dto;

import com.example.backoffice.admin.consts.AdminRole;
import lombok.Getter;

@Getter
public class UpdateAdminRoleResponse {
    private final Long id;
    private final String adminName;
    private final AdminRole newRole;

    public UpdateAdminRoleResponse(Long id, String adminName, AdminRole newRole) {
        this.id = id;
        this.adminName = adminName;
        this.newRole = newRole;
    }
}
