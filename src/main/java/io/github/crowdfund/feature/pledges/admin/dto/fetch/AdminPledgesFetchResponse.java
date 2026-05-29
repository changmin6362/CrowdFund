package io.github.crowdfund.feature.pledges.admin.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record AdminPledgesFetchResponse(
        @Schema(description = "후원 목록")
        List<PledgeSummary> pledges
) {
}
