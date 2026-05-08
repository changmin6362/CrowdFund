package io.github.authservice.crowdfund.feature.useraddress.response;

import java.time.LocalDateTime;

public record GetUserAddressResponse(
        String message,
        UserAddressInfo address
) {
}
