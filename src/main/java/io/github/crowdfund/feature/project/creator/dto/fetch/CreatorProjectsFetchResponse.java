package io.github.crowdfund.feature.project.creator.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CreatorProjectsFetchResponse(
        @Schema(description = "프로젝트 목록")
        List<ProjectInfo> projects
) {
}
