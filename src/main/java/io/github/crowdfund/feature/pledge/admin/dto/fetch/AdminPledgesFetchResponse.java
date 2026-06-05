package io.github.crowdfund.feature.pledge.admin.dto.fetch;

import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record AdminPledgesFetchResponse(
        @Schema(description = "후원 목록")
        List<PledgeSummary> pledges,

        @Schema(description = "다음 페이지 존재 여부", example = "true")
        boolean hasNext,

        @Schema(description = "다음 페이지 커서 정보")
        CursorRequest nextCursor
) {
}
