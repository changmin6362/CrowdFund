package io.github.authservice.crowdfund.feature.pledges.response;

public record GetPledgeDetailResponse(
        String message,
        PledgeDetail pledgeDetail
) {
}
