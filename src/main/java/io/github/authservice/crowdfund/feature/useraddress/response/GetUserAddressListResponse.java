package io.github.authservice.crowdfund.feature.useraddress.response;

import java.time.LocalDateTime;
import java.util.List;

public record GetUserAddressListResponse(
        String message,
        List<UserAddressInfo> addressList
) {
}
