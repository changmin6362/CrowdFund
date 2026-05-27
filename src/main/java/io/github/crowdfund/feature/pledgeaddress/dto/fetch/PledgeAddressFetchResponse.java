package io.github.crowdfund.feature.pledgeaddress.dto.fetch;

import io.github.crowdfund.feature.pledgeaddress.dto.PledgeAddressInfo;

public record PledgeAddressFetchResponse(
        PledgeAddressInfo pledgeAddress
) {
}
