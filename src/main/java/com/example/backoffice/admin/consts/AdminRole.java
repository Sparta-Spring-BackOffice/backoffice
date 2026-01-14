package com.example.backoffice.admin.consts;

import com.example.backoffice.admin.exception.NoSuchAdminRoleException;
import com.example.backoffice.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminRole {
    SUPER_ADMIN("SUPER_ADMIN"),
    OP_ADMIN("OP_ADMIN"),
    CS_ADMIN("CS_ADMIN");

    private final String roleName;

    public static AdminRole getRole(String roleName) {
        for (AdminRole role : AdminRole.values()) {
            if (role.roleName.equals(roleName)) {
                return role;
            }
        }
        throw new NoSuchAdminRoleException(ErrorCode.NO_SUCH_ROLE);
    }
}
