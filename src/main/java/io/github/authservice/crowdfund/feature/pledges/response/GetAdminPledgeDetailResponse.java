package io.github.authservice.crowdfund.feature.pledges.response;

public record GetAdminPledgeDetailResponse(
        String message,
        AdminPledgeDetail adminPledgeDetail
) {
}
