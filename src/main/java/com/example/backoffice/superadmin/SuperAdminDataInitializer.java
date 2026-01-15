package com.example.backoffice.superadmin;

import com.example.backoffice.admin.consts.AdminRole;
import com.example.backoffice.admin.consts.AdminStatus;
import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.common.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuperAdminDataInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {

        String encryptedPassword = passwordEncoder.encode("123456789");

        Administrator superAdmin = new Administrator(
                "함형우",
                "super@naver.com",
                encryptedPassword,
                "010-0000-1223",
                AdminRole.SUPER_ADMIN,
                AdminStatus.ACTIVE
        );

        adminRepository.save(superAdmin);
    }
}
