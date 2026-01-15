package com.example.backoffice.superadmin;

import com.example.backoffice.admin.consts.AdminRole;
import com.example.backoffice.admin.consts.AdminStatus;
import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.common.config.PasswordEncoder;
import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.entity.User;
import com.example.backoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuperAdminDataInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

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

        User user1 = new User(
                "김철수",
                "user1@naver.com",
                "010-1234-4444",
                UserStatus.ACTIVE
        );

        User user2 = new User(
                "박진수",
                "user2@naver.com",
                "010-3635-5866",
                UserStatus.NON_ACTIVE
        );

        User user3 = new User(
                "이민영",
                "user3@naver.com",
                "010-4567-2772",
                UserStatus.SUSPEND
        );

        adminRepository.save(superAdmin);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
}
