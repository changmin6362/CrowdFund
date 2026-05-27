package io.github.authservice.crowdfund.feature.pledgeaddress.dto.replace;

import io.github.authservice.crowdfund.feature.pledgeaddress.dto.PledgeAddressInfo;

public record PledgeAddressReplaceResponse(
        PledgeAddressInfo replacedPledgeAddress
) {
}
