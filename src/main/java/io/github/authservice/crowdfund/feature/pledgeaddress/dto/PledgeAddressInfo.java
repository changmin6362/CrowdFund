package io.github.authservice.crowdfund.feature.pledgeaddress.dto;

import java.time.LocalDateTime;

public record PledgeAddressInfo(
        Long pledgeAddressId,
        Long pledgeId,
        Long userId,
        String recipientName,
        String phone,
        String postalCode,
        String addressMain,
        String addressDetail,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
