package com.example.backoffice.user.service;

import com.example.backoffice.common.exception.ErrorCode;
import com.example.backoffice.order.repository.OrderRepository;
import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.dto.*;
import com.example.backoffice.user.entity.User;
import com.example.backoffice.user.exception.UserNotFoundException;
import com.example.backoffice.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Page<GetUserResponse> findAllUsers(String keyword, Pageable pageable, UserStatus status) {

        // 빈 문자열(keyword)을 null로 바꾸기 위함
        String searchKeyword = (keyword == null || keyword.isEmpty()) ? null : keyword;

        Page<User> users;

        if (searchKeyword != null && searchKeyword.contains("@")) {
            users = userRepository.findByEmailKeyword(searchKeyword,pageable,status);
        } else {
            users = userRepository.findByNameKeyword(searchKeyword,pageable,status);
        }

        List<Long> userIdList = users.map(User::getId).toList();
        orderRepository.findUserOrderDtoByUserID(userIdList)

        return users.map(user -> new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getStatus().getStatus(),
                user.getCreatedAt(),
                user.getModifiedAt(),
                user.getOrders().size(),
        ));
    }

    @Transactional(readOnly = true)
    public GetOneUserResponse findOneUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.NO_SUCH_USER)
        );
        return new GetOneUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getStatus().getStatus(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public UpdateUserResponse updateUser(Long userId, @Valid UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.NO_SUCH_USER)
        );
        user.updateUser(
                request.getName(),
                request.getEmail(),
                request.getPhoneNumber()
        );
        return new UpdateUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getStatus().getStatus(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public UpdateUserResponse updateUserStatus(Long userId, @Valid UpdateUserStatusRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.NO_SUCH_USER)
        );
        user.updateUserStatus(request.getStatus());

        return new UpdateUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getStatus().getStatus(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new UserNotFoundException(ErrorCode.NO_SUCH_USER);
        }
        userRepository.deleteById(userId);
    }
}
