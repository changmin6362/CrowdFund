package io.github.authservice.crowdfund.feature.project.request;

import jakarta.validation.constraints.NotBlank;

public record PatchProjectRequest(
        @NotBlank(message = "프로젝트 제목은 필수입니다.")
        String title,

        @NotBlank(message = "프로젝트 콘텐트 블럭 데이터는 필수입니다.")
        String contentBlocks
) {
}