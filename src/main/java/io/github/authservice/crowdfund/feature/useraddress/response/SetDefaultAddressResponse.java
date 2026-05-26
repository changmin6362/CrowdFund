package io.github.authservice.crowdfund.feature.useraddress.response;

public record SetDefaultAddressResponse(
        String message,
        DefaultAddressResult defaultAddressResult
) {
}
