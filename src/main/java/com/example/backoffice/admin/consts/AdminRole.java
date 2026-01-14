package com.example.backoffice.admin.consts;

import com.example.backoffice.admin.exception.NoSuchAdminRoleException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AdminRole {
    SUPER_ADMIN("super"),
    OP_ADMIN("op"),
    CS_ADMIN("cs");

    private final String roleName;

    public static AdminRole getRole(String roleName) {
        for (AdminRole role : AdminRole.values()) {
            if (role.roleName.equals(roleName)) {
                return role;
            }
        }
        throw new NoSuchAdminRoleException();
    }
}
