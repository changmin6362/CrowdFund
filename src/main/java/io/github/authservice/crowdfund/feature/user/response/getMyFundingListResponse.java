package io.github.authservice.crowdfund.feature.user.response;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

public record getMyFundingListResponse(
        String message,
        List<UserPledgeResponse> fundingList
) {
    public record UserPledgeResponse(
            Long pledgeId,
            Long projectId,
            String projectTitle,
            Long rewardId,
            String rewardTitle,
            BigDecimal amount,
            String status,
            LocalDateTime pledgedAt
    ) {}
}
