package io.github.crowdfund.feature.pledge.my.dto.fetch;

import io.github.crowdfund.domain.pledge.response.UserPledgeResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MyPledgesFetchResponse(
        @Schema(description = "유저가 후원한 프로젝트 목록")
        List<UserPledgeResponse> pledges
) {
}
