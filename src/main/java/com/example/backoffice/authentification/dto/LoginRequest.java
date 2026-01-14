package com.example.backoffice.authentification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class LoginRequest {
    @NotBlank
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    @NotBlank @Length(min = 8, message = "비밀번호는 {min}자 이상이어야 합니다.")
    private String password;
}
