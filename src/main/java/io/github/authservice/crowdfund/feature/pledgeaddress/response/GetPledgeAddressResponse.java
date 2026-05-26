package io.github.authservice.crowdfund.feature.pledgeaddress.response;

public record GetPledgeAddressResponse(
        String message,
        PledgeAddressInfo pledgeAddress
) {
}
