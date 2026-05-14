package io.github.authservice.crowdfund.feature.user.response;

import io.github.authservice.crowdfund.domain.pledge.response.UserPledgeResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

public record getMyFundingListResponse(
        String message,
        List<UserPledgeResponse> pledgeList
) {
}
