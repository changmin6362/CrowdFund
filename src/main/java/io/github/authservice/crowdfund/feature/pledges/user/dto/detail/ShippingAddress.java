package io.github.authservice.crowdfund.feature.pledges.user.dto.detail;

public record ShippingAddress(
        String recipientName,
        String recipientPhone,
        String address,
        String detailAddress,
        String postalCode
) {
}
