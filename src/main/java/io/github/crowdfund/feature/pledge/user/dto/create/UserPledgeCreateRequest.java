package io.github.crowdfund.feature.pledge.user.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UserPledgeCreateRequest(
        @Schema(description = "프로젝트 ID")
        @NotNull(message = "프로젝트 ID는 필수 입력 항목입니다.")
        Long project_id,

        @Schema(description = "리워드 ID")
        @NotNull(message = "리워드 ID는 필수 입력 항목입니다.")
        Long reward_id
) {
}
