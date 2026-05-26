package io.github.authservice.crowdfund.feature.useraddress.response;

public record DefaultAddressResult(
        Long addressId,
        boolean isDefault
) {
}
