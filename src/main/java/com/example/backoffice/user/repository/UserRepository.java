package com.example.backoffice.user.repository;

import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    // LIKE %:% = 정확히 일치하지 않고 단어가 포함되면 검색
    @Query(value = "SELECT u FROM User u " +
            "WHERE (:keyword IS NULL OR u.email LIKE %:keyword%) " +
            "AND (:status IS NULL OR u.status = :status)")
    Page<User> findByEmailKeyword(String keyword, Pageable pageable, UserStatus status);

    @Query(value = "SELECT u FROM User u " +
            "WHERE (:keyword IS NULL OR u.name LIKE %:keyword%) " +
            "AND (:status IS NULL OR u.status = :status)")
    Page<User> findByNameKeyword(String keyword, Pageable pageable, UserStatus status);

    @Query("SELECT COUNT(u) FROM User u WHERE u.status = :status")
    Long countByActiveStatus(UserStatus status);
}
