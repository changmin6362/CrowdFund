package io.github.crowdfund.feature.useraddress.dto.fetch;

import io.github.crowdfund.feature.useraddress.dto.UserAddressInfo;

import java.util.List;

public record UserAddressesFetchResponse(
        List<UserAddressInfo> addresses
) {
}
