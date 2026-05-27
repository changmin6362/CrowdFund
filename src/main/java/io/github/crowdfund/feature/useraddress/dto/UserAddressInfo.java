package io.github.crowdfund.feature.useraddress.dto;

import java.time.LocalDateTime;

public record UserAddressInfo(
        Long addressId,
        String recipientName,
        String phone,
        String postalCode,
        String addressMain,
        String addressDetail,
        boolean isDefault,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
