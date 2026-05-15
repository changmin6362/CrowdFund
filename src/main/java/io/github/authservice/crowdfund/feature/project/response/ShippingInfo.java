package io.github.authservice.crowdfund.feature.project.response;

public record ShippingInfo(
        Long addressId,
        Long userId,
        String recipientName,
        String phone,
        String postalCode,
        String addressMain,
        String addressDetail
) {
}
