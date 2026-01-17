package com.example.backoffice.superadmin;

import com.example.backoffice.admin.consts.AdminRole;
import com.example.backoffice.admin.consts.AdminStatus;
import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.common.config.PasswordEncoder;
import com.example.backoffice.order.consts.OrderStatus;
import com.example.backoffice.order.entity.Order;
import com.example.backoffice.order.repository.OrderRepository;
import com.example.backoffice.product.consts.ProductStatus;
import com.example.backoffice.product.entity.Product;
import com.example.backoffice.product.repository.ProductRepository;
import com.example.backoffice.review.entity.Review;
import com.example.backoffice.review.repository.ReviewRepository;
import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.entity.User;
import com.example.backoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class SuperAdminDataInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void run(String... args) throws Exception {

        String encryptedPassword = passwordEncoder.encode("12345678");

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


        Product product1 = new Product(
                "아이폰 15",
                "전자기기",
                new BigDecimal("1500000"),
                10L,
                ProductStatus.FOR_SALE,
                superAdmin
        );

        Product product2 = new Product(
                "맥북 프로",
                "전자기기",
                new BigDecimal("3000000"),
                5L,
                ProductStatus.FOR_SALE,
                superAdmin
        );

        productRepository.save(product1);
        productRepository.save(product2);

// ===== 주문 생성 =====
        Order order1 = new Order(
                "ORDER-20260113-001",
                1L,
                new BigDecimal(String.valueOf(product1.getPrice())),
                OrderStatus.READY,
                user1,
                product1
        );

        Order order2 = new Order(
                "ORDER-20260116-002",
                2L,
                new BigDecimal(String.valueOf(product2.getPrice())),
                OrderStatus.CANCELLED,
                user2,
                product2
        );

        orderRepository.save(order1);
        orderRepository.save(order2);

// ===== 리뷰 생성 =====
        Review review1 = new Review(
                5,
                "배송도 빠르고 상품 상태가 정말 좋습니다.",
                order1,
                product1
        );

        Review review2 = new Review(
                3,
                "무난한 제품이지만 가격이 조금 아쉬워요.",
                order2,
                product2
        );

        reviewRepository.save(review1);
        reviewRepository.save(review2);

    }
}
