package com.example.backoffice.user.repository;

import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "SELECT u FROM User u WHERE " +
            "(:keyword IS NULL OR u.name LIKE %:keyword% OR u.email LIKE %:keyword%) AND " +
            "(:status IS NULL OR u.status = :status)")
    Page<User> findAll(
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("status") UserStatus status
    );

}
