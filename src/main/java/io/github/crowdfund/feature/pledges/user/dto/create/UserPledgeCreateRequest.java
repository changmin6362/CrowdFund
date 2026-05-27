package io.github.crowdfund.feature.pledges.user.dto.create;

import jakarta.validation.constraints.NotNull;

public record UserPledgeCreateRequest(

        @NotNull(message = "프로젝트 ID는 필수 입력 항목입니다.")
        Long project_id,

        @NotNull(message = "리워드 ID는 필수 입력 항목입니다.")
        Long reward_id
) {
}
