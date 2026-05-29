package io.github.crowdfund.feature.useraddress.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserAddressCreateResponse(
        @Schema(description = "유저 배송지 ID")
        Long addressId
) {
}
