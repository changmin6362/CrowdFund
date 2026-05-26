package io.github.authservice.crowdfund.feature.pledges.response;

public record PledgeDetail(
        Long id,
        Long user_id,
        Long project_id,
        Long reward_id,
        Long amount,
        String created_at
) {
}
