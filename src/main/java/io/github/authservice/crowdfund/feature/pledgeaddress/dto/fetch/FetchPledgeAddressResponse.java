package io.github.authservice.crowdfund.feature.pledgeaddress.dto.fetch;

import io.github.authservice.crowdfund.feature.pledgeaddress.dto.PledgeAddressInfo;

public record FetchPledgeAddressResponse(
        PledgeAddressInfo pledgeAddress
) {
}
