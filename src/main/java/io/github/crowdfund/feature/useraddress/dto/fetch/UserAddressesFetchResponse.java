package io.github.crowdfund.feature.useraddress.dto.fetch;

import io.github.crowdfund.feature.useraddress.dto.UserAddressInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record UserAddressesFetchResponse(
        @Schema(description = "배송지 목록")
        List<UserAddressInfo> addresses
) {
}
