package io.github.authservice.crowdfund.domain.useraddress;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("user_address")
public record UserAddress(
    @Id Long id,
    Long userId,
    String recipientName,
    String phone,
    String postalCode,
    String addressMain,
    String addressDetail,
    Boolean isDefault,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
