package io.github.crowdfund.feature.project.user.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record UserProjectFetchResponse(
        @Schema(description = "유저가 참여한 프로젝트 목록")
        List<ProjectElement> projectList,

        @Schema(description = "다음 페이지 존재 여부")
        Boolean hasNext,

        @Schema(description = "다음 페이지 커서")
        NextCursor nextCursor
) {
}
