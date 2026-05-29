package io.github.crowdfund.feature.useraddress.dto.set;

import io.swagger.v3.oas.annotations.media.Schema;

public record DefaultAddressResult(
        @Schema(description = "배송지 ID")
        Long addressId,

        @Schema(description = "기본 배송지 여부")
        boolean isDefault
) {
}
