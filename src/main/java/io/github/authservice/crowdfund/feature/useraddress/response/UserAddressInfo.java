package io.github.authservice.crowdfund.feature.useraddress.response;

import java.time.LocalDateTime;

public record UserAddressInfo(
        Integer id,
        String recipientName,
        String phone,
        String postal_code,
        String addressMain,
        String addressDetail,
        boolean isDefault,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
