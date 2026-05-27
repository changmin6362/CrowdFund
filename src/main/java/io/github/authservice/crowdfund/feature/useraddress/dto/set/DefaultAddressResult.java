package io.github.authservice.crowdfund.feature.useraddress.dto.set;

public record DefaultAddressResult(
        Long addressId,
        boolean isDefault
) {
}
