package com.aoxx.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이름으로 찾는 커스텀 메서드
    Optional<User> findByName(String name);
}