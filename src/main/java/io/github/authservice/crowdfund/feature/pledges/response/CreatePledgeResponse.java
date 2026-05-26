package io.github.authservice.crowdfund.feature.pledges.response;

public record CreatePledgeResponse(
        String message,
        Long user_id,
        Long project_id,
        Long reward_id
) {
}
