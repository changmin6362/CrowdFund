package io.github.crowdfund.feature.useraddress.dto.set;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserAddressSetResponse(
        @Schema(description = "기본 배송지 정보")
        DefaultAddressResult defaultAddressResult
) {
}
