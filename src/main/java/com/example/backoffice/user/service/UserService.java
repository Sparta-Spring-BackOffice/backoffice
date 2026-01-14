package com.example.backoffice.user.service;

import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.dto.GetUserResponse;
import com.example.backoffice.user.entity.User;
import com.example.backoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<GetUserResponse> findAll(String keyword, Pageable pageable, UserStatus status) {

        Page<User> users = userRepository.findAll(keyword, pageable, status);

        return users.map(user -> new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getStatus().name(),
                user.getCreatedAt()
        ));
    }
}
