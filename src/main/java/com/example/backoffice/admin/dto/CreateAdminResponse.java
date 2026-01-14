package com.example.backoffice.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateAdminResponse {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @Min(value = 8)
    private String password;
    @NotBlank
    @Pattern(
            regexp = "^010-?\\d{4}-?\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다."
    )
    private String phone;
    @NotBlank
    private String role;
}
