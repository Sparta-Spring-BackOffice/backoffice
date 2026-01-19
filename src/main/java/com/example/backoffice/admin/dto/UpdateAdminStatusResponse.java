package com.example.backoffice.admin.dto;

import com.example.backoffice.admin.consts.AdminStatus;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UpdateAdminStatusResponse {
    private final Long id;
    private final AdminStatus adminStatus;
    private final LocalDateTime modifiedAt;

    public UpdateAdminStatusResponse(Long id, AdminStatus adminStatus, LocalDateTime modifiedAt) {
        this.id = id;
        this.adminStatus = adminStatus;
        this.modifiedAt = modifiedAt;
    }
}
