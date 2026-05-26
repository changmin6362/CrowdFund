package io.github.authservice.crowdfund.feature.useraddress.response;

import java.util.List;

public record GetUserAddressesResponse(
        List<UserAddressInfo> addresses
) {
}
