package com.example.backoffice.admin.consts;

import com.example.backoffice.admin.exception.NoSuchAdminStatusException;
import com.example.backoffice.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminStatus {
    ACTIVE("active"),
    NON_ACTIVE("non_active"),
    SUSPENDED("suspended"),
    PENDING("pending"),
    DENIED("denied");

    private final String statusName;

    public static AdminStatus getStatus(String statusName) {
        for(AdminStatus status : AdminStatus.values()) {
            if(status.statusName.equals(statusName)){
                return status;
            }
        }
        throw new NoSuchAdminStatusException(ErrorCode.NO_SUCH_STATUS);
    }
}
