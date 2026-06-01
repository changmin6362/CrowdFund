package io.github.crowdfund.feature.pledgeaddress.dto.replace;

import io.swagger.v3.oas.annotations.media.Schema;

public record PledgeAddressReplaceRequest(
        @Schema(description = "참여한 후원의 배송 정보 ID", example = "1")
        long addressId
) {
}
