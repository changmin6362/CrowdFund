package io.github.authservice.crowdfund.feature.useraddress.response;

public record CreateUserAddressResponse(
        String message,
        Long addressId
) {
}
