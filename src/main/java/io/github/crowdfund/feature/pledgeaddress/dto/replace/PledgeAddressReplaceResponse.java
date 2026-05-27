package io.github.crowdfund.feature.pledgeaddress.dto.replace;

import io.github.crowdfund.feature.pledgeaddress.dto.PledgeAddressInfo;

public record PledgeAddressReplaceResponse(
        PledgeAddressInfo replacedPledgeAddress
) {
}
