package io.github.authservice.crowdfund.feature.project.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record UpdateProjectRequest(
        @NotBlank(message = "프로젝트 제목은 필수입니다.")
        String title,

        @NotBlank(message = "프로젝트 콘텐트 블럭 데이터는 필수입니다.")
        String contentBlocks
) {
}