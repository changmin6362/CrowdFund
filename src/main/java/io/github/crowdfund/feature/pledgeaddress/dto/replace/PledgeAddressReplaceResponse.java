package io.github.crowdfund.feature.pledgeaddress.dto.replace;

import io.github.crowdfund.feature.pledgeaddress.dto.PledgeAddressInfo;
import io.swagger.v3.oas.annotations.media.Schema;

public record PledgeAddressReplaceResponse(
        @Schema(description = "교체된 후원의 배송 정보")
        PledgeAddressInfo replacedPledgeAddress
) {
}
