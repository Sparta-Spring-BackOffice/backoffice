package com.example.backoffice.admin.repository;

import com.example.backoffice.admin.consts.AdminStatus;
import com.example.backoffice.admin.entity.Administrator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Administrator, Long> {

    @Query("SELECT a FROM Administrator a " +
            "WHERE (:keyword IS NULL OR a.email LIKE %:keyword%)" +
            " AND (:role IS NULL OR a.role = :role)" +
            " AND (:status Is NULL OR a.status = :status)")
    Page<Administrator> findByEmailKeyword(@Param("keyword") String keyword, @Param("role")String role, @Param("status") String status, Pageable pageable);

    @Query("SELECT a FROM Administrator a " +
            "WHERE (:keyword IS NULL OR a.name like %:keyword%)" +
            " AND (:role IS NULL OR a.role = :role)" +
            " AND (:status Is NULL OR a.status = :status)")
    Page<Administrator> findByNameKeyword(@Param("keyword") String keyword, @Param("role")String role, @Param("status") String status, Pageable pageable);

    boolean existsByEmail(String email);
    Optional<Administrator> findByEmail(String email);

    @Query("SELECT COUNT(a) FROM Administrator a WHERE a.status = :status")
    Long countByActiveStatus(AdminStatus status);
}
