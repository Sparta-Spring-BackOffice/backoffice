package com.example.backoffice.admin.service;

import com.example.backoffice.admin.dto.GetAdminResponse;
import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public List<GetAdminResponse> getAllAdmins(String keyword, String role, String status, Pageable pageable) {

        Page<Administrator> findAdmins;

        if (keyword.contains("@")){
            findAdmins = adminRepository.findByEmailKeyword(keyword, role, status, pageable);
        } else {
            findAdmins = adminRepository.findByNameKeyword(keyword, role, status, pageable);
        }

        return findAdmins.stream()
                .map(administrator -> new GetAdminResponse(
                        administrator.getId(),
                        administrator.getName(),
                        administrator.getEmail(),
                        administrator.getPhone(),
                        administrator.getRole().getRoleName(),
                        administrator.getStatus().getStatus(),
                        administrator.getCreatedAt(),
                        administrator.getModifiedAt(),
                        findAdmins.getNumber(),
                        findAdmins.getSize(),
                        findAdmins.getTotalElements(),
                        findAdmins.getTotalPages()
                )).toList();

    }
}
