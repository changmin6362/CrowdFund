package io.github.crowdfund.feature.pledge.user.dto.detail;

public record ShippingAddress(
        String recipientName,
        String recipientPhone,
        String address,
        String detailAddress,
        String postalCode
) {
}
