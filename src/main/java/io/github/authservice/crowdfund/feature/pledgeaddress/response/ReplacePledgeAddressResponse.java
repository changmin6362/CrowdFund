package io.github.authservice.crowdfund.feature.pledgeaddress.response;

public record ReplacePledgeAddressResponse(
        String message,
        PledgeAddressInfo replacedPledgeAddress
) {
}
