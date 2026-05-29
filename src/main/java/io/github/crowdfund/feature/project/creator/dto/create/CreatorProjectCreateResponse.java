package io.github.crowdfund.feature.project.creator.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreatorProjectCreateResponse(
        @Schema(description = "생성된 프로젝트 ID")
        Long createdProjectId
) {
}