package com.example.backoffice.dashboard.service;

import com.example.backoffice.admin.consts.AdminStatus;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.dashboard.dto.SummaryStatsResponse;
import com.example.backoffice.order.repository.OrderRepository;
import com.example.backoffice.product.entity.Product;
import com.example.backoffice.product.repository.ProductRepository;
import com.example.backoffice.review.repository.ReviewRepository;
import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DashboardService {
        private final ProductRepository productRepository;
        private final AdminRepository adminRepository;
        private final UserRepository userRepository;
        private final OrderRepository orderRepository;
        private final ReviewRepository reviewRepository;

    @Transactional
    public SummaryStatsResponse summaryStats() {
        Long totalAdmin = adminRepository.count();
        Long activeAdmin = adminRepository.countByActiveStatus(AdminStatus.ACTIVE);
        Long totalUser = userRepository.count();
        Long activeUser = userRepository.countByActiveStatus(UserStatus.ACTIVE);
        Long totalProduct = productRepository.count();
        Long lowStockProduct = productRepository.countByLowStock();
        Long totalOrder = orderRepository.count();
        Long todayOrder = orderRepository.countByCreatedAt(LocalDateTime.now());
        Long totalReview = reviewRepository.count();
        Double averageReview = reviewRepository.findAverageRating();

        return new SummaryStatsResponse(
                totalAdmin,
                activeAdmin,
                totalUser,
                activeUser,
                totalProduct,
                lowStockProduct,
                totalOrder,
                todayOrder,
                totalReview,
                averageReview
        );
    }
}
