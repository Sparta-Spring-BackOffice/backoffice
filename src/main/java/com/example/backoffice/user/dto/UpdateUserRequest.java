package com.example.backoffice.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email
    private String email;
    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @Pattern(
            regexp = "^010-?\\d{4}-?\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다."
    )
    private String phoneNumber;
}
