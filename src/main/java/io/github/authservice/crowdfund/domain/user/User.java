package io.github.authservice.crowdfund.domain.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("user")
public record User(
    @Id Long id,
    String email,
    String password,
    String nickname,
    String name,
    String phone,
    String role,
    LocalDateTime createdAt
) {}
