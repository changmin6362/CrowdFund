package io.github.authservice.crowdfund.feature.pledges.request;

import jakarta.validation.constraints.NotBlank;

public record PledgeRequest(
        @NotBlank
        Long user_id,

        @NotBlank
        Long project_id,

        @NotBlank
        Long reward_id,

        @NotBlank
        Long amount,

        @NotBlank
        String created_at
) {
}
