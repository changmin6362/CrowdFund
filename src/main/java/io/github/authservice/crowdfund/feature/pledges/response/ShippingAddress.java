package io.github.authservice.crowdfund.feature.pledges.response;

public record ShippingAddress(
        String recipientName,
        String recipientPhone,
        String address,
        String detailAddress,
        String postalCode
) {
}
