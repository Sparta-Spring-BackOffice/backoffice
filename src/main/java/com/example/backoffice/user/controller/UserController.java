package com.example.backoffice.user.controller;

import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.dto.GetOneUserResponse;
import com.example.backoffice.user.dto.GetUserResponse;
import com.example.backoffice.user.dto.UpdateUserRequest;
import com.example.backoffice.user.dto.UpdateUserResponse;
import com.example.backoffice.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/admin/users")
    public ResponseEntity<Page<GetUserResponse>> getUsers(
            @RequestParam String keyword,
            @PageableDefault Pageable pageable,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam UserStatus status
    ) {
        Pageable converted = PageRequest.of(
                page - 1,
                pageable.getPageSize(),
                pageable.getSort()
        );

        return ResponseEntity.ok(userService.findAllUsers(keyword, converted, status));
    }

    @GetMapping("/admin/users/{userId}")
    public ResponseEntity<GetOneUserResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findOneUser(userId));
    }

    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request
    ){
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }
}
