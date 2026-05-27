package io.github.authservice.crowdfund.feature.user.dto.extract;

import io.github.authservice.crowdfund.domain.pledge.response.UserPledgeResponse;

import java.util.List;

public record UserExtractResponse(
        List<UserPledgeResponse> pledgeList
) {
}
