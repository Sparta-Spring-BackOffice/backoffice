package com.example.backoffice.user.controller;

import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.dto.GetUserResponse;
import com.example.backoffice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/admin/users")
    public ResponseEntity<Page<GetUserResponse>> getUsers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOder,
            @RequestParam UserStatus status
    ) {
        Sort sort = sortOder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        return ResponseEntity.ok(userService.findAll(keyword, pageable, status));
    }
}
