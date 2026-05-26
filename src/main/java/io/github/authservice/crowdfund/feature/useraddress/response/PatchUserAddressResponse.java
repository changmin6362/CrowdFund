package io.github.authservice.crowdfund.feature.useraddress.response;

public record PatchUserAddressResponse(
        String message,
        UserAddressInfo updatedAddress
) {
}
