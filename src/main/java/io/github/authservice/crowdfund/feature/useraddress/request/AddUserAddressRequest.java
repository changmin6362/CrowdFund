package io.github.authservice.crowdfund.feature.useraddress.request;

public record AddUserAddressRequest(
        String recipientName,
        String phone,
        String postal_code,
        String addressMain,
        String addressDetail
) {
}
