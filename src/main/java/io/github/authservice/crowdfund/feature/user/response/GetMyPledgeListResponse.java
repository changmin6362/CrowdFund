package io.github.authservice.crowdfund.feature.user.response;

import io.github.authservice.crowdfund.domain.pledge.response.UserPledgeResponse;

import java.util.List;

public record GetMyPledgeListResponse(
        String message,
        List<UserPledgeResponse> pledgeList
) {
}
