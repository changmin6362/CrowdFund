package io.github.crowdfund.feature.pledgeaddress.dto.fetch;

import io.github.crowdfund.feature.pledgeaddress.dto.PledgeAddressInfo;
import io.swagger.v3.oas.annotations.media.Schema;

public record PledgeAddressFetchResponse(
        @Schema(description = "참여한 후원의 배송 정보")
        PledgeAddressInfo pledgeAddress
) {
}
