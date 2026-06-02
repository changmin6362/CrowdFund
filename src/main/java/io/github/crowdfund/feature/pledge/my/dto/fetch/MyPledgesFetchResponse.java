package io.github.crowdfund.feature.pledge.my.dto.fetch;

import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MyPledgesFetchResponse(
        @Schema(description = "유저가 후원한 프로젝트 목록")
        List<MyPledgeInfo> pledges,

        @Schema(description = "다음 페이지 존재 여부", example = "true")
        boolean hasNext,

        @Schema(description = "다음 페이지 커서 정보")
        CursorRequest nextCursor
) {
}
