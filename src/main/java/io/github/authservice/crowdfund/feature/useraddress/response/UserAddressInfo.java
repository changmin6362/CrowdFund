package io.github.authservice.crowdfund.feature.useraddress.response;

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
