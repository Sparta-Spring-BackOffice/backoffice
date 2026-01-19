package com.example.backoffice.jwt;

import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.exception.AdminNotFoundException;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.common.responsecode.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDetailService implements UserDetailsService {
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return adminRepository.findByEmail(email)
                .map(this::createAdminDetails)
                .orElseThrow(() -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );
    }

    private UserDetails createAdminDetails(Administrator administrator) {
        return AdminDetails.builder()
                .administrator(administrator)
                .build();
    }
}
