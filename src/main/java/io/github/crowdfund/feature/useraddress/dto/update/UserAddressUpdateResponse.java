package io.github.crowdfund.feature.useraddress.dto.update;

import io.github.crowdfund.feature.useraddress.dto.UserAddressInfo;

public record UserAddressUpdateResponse(
        UserAddressInfo updatedAddress
) {
}
