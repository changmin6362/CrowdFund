package io.github.crowdfund.feature.user.dto.extract;

import io.github.crowdfund.domain.pledge.response.UserPledgeResponse;

import java.util.List;

public record UserExtractResponse(
        List<UserPledgeResponse> pledgeList
) {
}
