package com.example.backoffice.user.controller;

import com.example.backoffice.common.dto.SuccessResponse;
import com.example.backoffice.common.responsecode.ResponseProcess;
import com.example.backoffice.common.responsecode.SuccessCode;
import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.dto.*;
import com.example.backoffice.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/admin/users")
    public ResponseEntity<SuccessResponse<Page<GetUserResponse>>> getUsers(
            @RequestParam(required = false) String keyword,
            @PageableDefault Pageable pageable,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) UserStatus status
    ) {
        Pageable converted = PageRequest.of(
                page - 1,
                pageable.getPageSize(),
                pageable.getSort()
        );
        return ResponseProcess.responseWithBody(SuccessCode.READ_SUCCESS, userService.findAllUsers(keyword, converted, status));
    }

    @GetMapping("/admin/users/{userId}")
    public ResponseEntity<SuccessResponse<GetOneUserResponse>> getUser(@PathVariable Long userId) {
        return ResponseProcess.responseWithBody(SuccessCode.READ_SUCCESS, userService.findOneUser(userId));
    }

    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<SuccessResponse<UpdateUserResponse>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request
    ){

        return ResponseProcess.responseWithBody(SuccessCode.UPDATE_SUCCESS, userService.updateUser(userId, request));
    }

    @PutMapping("/admin/users/status/{userId}")
    public ResponseEntity<SuccessResponse<UpdateUserResponse>> updateUserStatus(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserStatusRequest request
    ){
        return ResponseProcess.responseWithBody(SuccessCode.UPDATE_SUCCESS, userService.updateUserStatus(userId, request));
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<SuccessResponse<Void>> deleteUser( @PathVariable Long userId) {
        userService.delete(userId);
        return ResponseProcess.responseWithBuild(SuccessCode.DELETE_SUCCESS, null);
    }
}
