package com.example.backoffice.jwt;

import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.LoginDeniedException;
import com.example.backoffice.authentification.exception.LoginFailException;
import lombok.Builder;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@Getter
public class AdminDetails implements UserDetails {

    private final Administrator administrator;

    public AdminDetails(Administrator administrator) {this.administrator = administrator;}

    @Override
    public String getUsername() {
        return administrator.getEmail();
    }

    @Override
    public @Nullable String getPassword() {
        return administrator.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(administrator.getRole().toAuthority());
    }

    @Override
    public boolean isEnabled() {
        return switch (administrator.getStatus()) {
            case ACTIVE ->  true;
            case PENDING -> throw new LoginFailException(AuthErrorCode.LOGIN_PENDING_ERROR);
            case DENIED -> throw new LoginDeniedException(AuthErrorCode.LOGIN_DENIED_ERROR, administrator.getDeclineFor());
            case SUSPENDED -> throw new LoginFailException(AuthErrorCode.LOGIN_SUSPENDED_ERROR);
            case NON_ACTIVE -> throw new LoginFailException(AuthErrorCode.LOGIN_NON_ACTIVE_ERROR);
        };
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
