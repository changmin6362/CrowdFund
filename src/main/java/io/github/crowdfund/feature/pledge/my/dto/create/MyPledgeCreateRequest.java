package io.github.crowdfund.feature.pledge.my.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MyPledgeCreateRequest(
        @Schema(description = "프로젝트 ID", example = "2")
        @NotNull(message = "프로젝트 ID는 필수 입력 항목입니다.")
        Long projectId,

        @Schema(description = "리워드 ID", example = "1")
        @NotNull(message = "리워드 ID는 필수 입력 항목입니다.")
        Long rewardId
) {
}
