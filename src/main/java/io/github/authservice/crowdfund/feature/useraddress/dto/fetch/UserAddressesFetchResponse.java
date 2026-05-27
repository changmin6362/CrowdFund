package io.github.authservice.crowdfund.feature.useraddress.dto.fetch;

import io.github.authservice.crowdfund.feature.useraddress.dto.UserAddressInfo;

import java.util.List;

public record UserAddressesFetchResponse(
        List<UserAddressInfo> addresses
) {
}
