package com.example.backoffice.user.service;

import com.example.backoffice.common.exception.ErrorCode;
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

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<GetUserResponse> findAllUsers(String keyword, Pageable pageable, UserStatus status) {

        Page<User> users;

        if (keyword.contains("@")) {
            users = userRepository.findByEmailKeyword(keyword,pageable,status);
        } else {
            users = userRepository.findByNameKeyword(keyword,pageable,status);
        }

        return users.map(user -> new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getStatus().getStatus(),
                user.getCreatedAt(),
                user.getModifiedAt()
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
}
