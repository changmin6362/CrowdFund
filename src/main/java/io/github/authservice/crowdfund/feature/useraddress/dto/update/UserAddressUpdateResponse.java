package io.github.authservice.crowdfund.feature.useraddress.dto.update;

import io.github.authservice.crowdfund.feature.useraddress.dto.UserAddressInfo;

public record UserAddressUpdateResponse(
        UserAddressInfo updatedAddress
) {
}
