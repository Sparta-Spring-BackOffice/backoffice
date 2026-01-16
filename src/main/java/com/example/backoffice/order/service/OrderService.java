package com.example.backoffice.order.service;

import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.exception.AdminNotFoundException;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.common.exception.ErrorCode;
import com.example.backoffice.order.consts.OrderStatus;
import com.example.backoffice.order.dto.CreateOrderRequest;
import com.example.backoffice.order.dto.CreateOrderResponse;
import com.example.backoffice.order.dto.GetOneOrderResponse;
import com.example.backoffice.order.dto.GetOrderResponse;
import com.example.backoffice.order.entity.Order;
import com.example.backoffice.order.repository.OrderRepository;
import com.example.backoffice.product.consts.ProductStatus;
import com.example.backoffice.product.entity.Product;
import com.example.backoffice.product.exception.ProductNotFoundException;
import com.example.backoffice.product.repository.ProductRepository;
import com.example.backoffice.user.entity.User;
import com.example.backoffice.user.exception.UserNotFoundException;
import com.example.backoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request, Long adminId) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new ProductNotFoundException(ErrorCode.NO_SUCH_PRODUCT)
        );
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.NO_SUCH_USER)
        );

        Administrator admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );

        if (request.getQuantity() > product.getStock()) { // 에러 코드 및 멘트 고민중, 수정 예정
            throw new IllegalArgumentException("재고가 주문 수량보다 부족하여 주문할 수 없습니다.");
        }
        if (product.getStatus() == ProductStatus.DISCONTINUED) { // 에러 코드 및 멘트 고민중, 수정 예정
            throw new IllegalArgumentException("단종된 상품은 주문할 수 없습니다.");
        }

        if (product.getStatus() == ProductStatus.SOLD_OUT) { // 에러 코드 및 멘트 고민중, 수정 예정
            throw new IllegalArgumentException("품절된 상품은 주문할 수 없습니다.");
        }
        BigDecimal amount = BigDecimal.valueOf(request.getQuantity()).multiply(product.getPrice());

        product.updateStock(product.getStock() - request.getQuantity());
        String orderNumber = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)+ "-"
                + UUID.randomUUID().toString().substring(0,8);

        Order order = new Order(orderNumber, request.getQuantity(), amount, OrderStatus.READY, user, product);
        Order savedOrder = orderRepository.save(order);

        return new CreateOrderResponse(
                savedOrder.getId(),
                savedOrder.getCreatedAt(),
                savedOrder.getOrderNumber(),
                savedOrder.getStatus(),
                amount,
                savedOrder.getUser().getId(),
                admin.getId()
        );
    }

    @Transactional(readOnly = true)
    public Page<GetOrderResponse> findAllOrders(String keyword, Pageable pageable, OrderStatus status) {

        String searchKeyword = (keyword == null || keyword.isEmpty()) ? null : keyword;

        Page<Order> orders;

        if (searchKeyword != null && searchKeyword.chars().anyMatch(Character::isDigit)) {
            orders = orderRepository.findByOrderNumberKeyword(searchKeyword,pageable,status);
        } else {
            orders = orderRepository.findByNameKeyword(searchKeyword,pageable,status);
        }

        return orders.map(order -> new GetOrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getUser().getName(),
                order.getProduct().getName(),
                order.getProduct().getPrice(),
                order.getQuantity(),
                order.getAmount(),
                order.getCreatedAt(),
                order.getModifiedAt(),
                order.getStatus().getStatusName(),
                order.getProduct().getAdministrator().getName()
        ));
    }

    @Transactional(readOnly = true)
    public GetOneOrderResponse findOneOrder(Long orderId, boolean isAdmin) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 주문입니다.") // 전역예외처리 예정
        );

        return new GetOneOrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getUser().getName(),
                order.getProduct().getName(),
                order.getProduct().getPrice(),
                order.getQuantity(),
                order.getAmount(),
                order.getCreatedAt(),
                order.getModifiedAt(),
                order.getStatus().getStatusName(),
                // 어드민 주문일 경우 데이터 아닐경우 null
                isAdmin ? order.getProduct().getAdministrator().getName() : null,
                isAdmin ? order.getProduct().getAdministrator().getEmail(): null,
                isAdmin ? order.getProduct().getAdministrator().getStatus().getStatusName(): null
        );
    }
}
