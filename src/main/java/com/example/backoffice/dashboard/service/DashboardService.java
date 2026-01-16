package com.example.backoffice.dashboard.service;

import com.example.backoffice.admin.consts.AdminStatus;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.dashboard.dto.*;
import com.example.backoffice.order.consts.OrderStatus;
import com.example.backoffice.order.entity.Order;
import com.example.backoffice.order.repository.OrderRepository;
import com.example.backoffice.product.consts.ProductStatus;
import com.example.backoffice.product.repository.ProductRepository;
import com.example.backoffice.review.repository.ReviewRepository;
import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        Long totalAdmin = adminRepository.count();
        Long activeAdmin = adminRepository.countByActiveStatus(AdminStatus.ACTIVE);
        Long totalUser = userRepository.count();
        Long activeUser = userRepository.countByActiveStatus(UserStatus.ACTIVE);
        Long totalProduct = productRepository.count();
        Long lowStockProduct = productRepository.countByLowStock();
        Long totalOrder = orderRepository.count();
        Long todayOrder = orderRepository.countByCreatedAt(startOfDay, endOfDay);
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

    @Transactional
    public WidgetsStatsResponse widgetsStats() {

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        BigDecimal totalSales = orderRepository.sumTotalSales();
        BigDecimal todaySales = orderRepository.sumTodaySales(startOfDay, endOfDay);
        Long orderReady = orderRepository.countByReadyStatus(OrderStatus.READY);
        Long orderInTransit = orderRepository.countByInTransitStatus(OrderStatus.IN_TRANSIT);
        Long orderCompleted = orderRepository.countByCompletedStatus(OrderStatus.COMPLETED);
        Long lowStockProduct = productRepository.countByLowStock();
        Long soldOutProduct = productRepository.countBySoldOutStatus(ProductStatus.SOLD_OUT);

        return new WidgetsStatsResponse(
                totalSales,
                todaySales,
                orderReady,
                orderInTransit,
                orderCompleted,
                lowStockProduct,
                soldOutProduct
        );
    }

    public ChartsStatsResponse chartsStats() {
        List<ReviewRatingDto> countByReviewRating = reviewRepository.countByReviewByRating();
        List<UserStatusDto> countByUserStatus = userRepository.countByUserByStatus();
        List<CategoryDto> countByCategory = productRepository.countByCategoryByName();

        return new ChartsStatsResponse(countByReviewRating, countByUserStatus, countByCategory);
    }

    public List<LateOrderListResponse> lateOrderList() {
        List<Order> orders = orderRepository.findTop10ByOrderByCreatedAtDesc();
        List<LateOrderListResponse> dtos = new ArrayList<>();
        for (Order order : orders) {
            LateOrderListResponse dto = new LateOrderListResponse(
                    order.getOrderNumber(),
                    order.getUser().getName(),
                    order.getProduct().getName(),
                    order.getAmount(),
                    order.getStatus()
            );
            dtos.add(dto);
        }
        return dtos;
    }
}
