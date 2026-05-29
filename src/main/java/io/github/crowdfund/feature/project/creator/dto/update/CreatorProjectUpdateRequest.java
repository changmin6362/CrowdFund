package io.github.crowdfund.feature.project.creator.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreatorProjectUpdateRequest(
        @Schema(description = "프로젝트 제목")
        @NotBlank(message = "프로젝트 제목은 필수입니다.")
        String title,

        @Schema(description = "프로젝트 콘텐트 블럭 데이터")
        @NotBlank(message = "프로젝트 콘텐트 블럭 데이터는 필수입니다.")
        String contentBlocks
) {
}