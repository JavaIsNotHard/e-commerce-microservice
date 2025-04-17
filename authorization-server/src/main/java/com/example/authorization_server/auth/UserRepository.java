package com.example.authorization_server.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<SecurityUser, Long> {
    boolean existsByUsername(String username);
    Optional<SecurityUser> findByUsername(String username);
}
