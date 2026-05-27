package io.github.crowdfund.feature.project.creator.dto.update;

import jakarta.validation.constraints.NotBlank;

public record CreatorProjectUpdateRequest(
        @NotBlank(message = "프로젝트 제목은 필수입니다.")
        String title,

        @NotBlank(message = "프로젝트 콘텐트 블럭 데이터는 필수입니다.")
        String contentBlocks
) {
}