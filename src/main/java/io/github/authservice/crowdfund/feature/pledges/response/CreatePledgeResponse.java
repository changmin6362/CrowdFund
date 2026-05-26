package io.github.authservice.crowdfund.feature.pledges.response;

public record CreatePledgeResponse(
        String message,
        Long pledgeId
) {
}
