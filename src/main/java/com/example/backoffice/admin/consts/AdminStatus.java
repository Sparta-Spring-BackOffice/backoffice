package com.example.backoffice.admin.consts;

import com.example.backoffice.admin.exception.NoSuchAdminStatusException;
import com.example.backoffice.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminStatus {
    ACTIVE("ACTIVE"),
    NON_ACTIVE("NON_ACTIVE"),
    SUSPENDED("SUSPENDED"),
    PENDING("PENDING"),
    DENIED("DENIED");

    private final String status;

    public static AdminStatus getStatus(String status) {
        for (AdminStatus adminStatus : AdminStatus.values()) {
            if (adminStatus.status.equals(status)) {
                return adminStatus;
            }
        }
        throw new NoSuchAdminStatus(ErrorCode.NO_SUCH_STATUS);
    }
}
