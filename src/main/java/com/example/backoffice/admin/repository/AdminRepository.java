package com.example.backoffice.admin.repository;

import com.example.backoffice.admin.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Administrator, Long> {
}
