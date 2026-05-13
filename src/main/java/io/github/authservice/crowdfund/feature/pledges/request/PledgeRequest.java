package io.github.authservice.crowdfund.feature.pledges.request;

import jakarta.validation.constraints.NotBlank;

public record PledgeRequest(
        @NotBlank(message = "사용자 ID는 필수 입력 항목입니다.")
        Long user_id,

        @NotBlank(message = "프로젝트 ID는 필수 입력 항목입니다.")
        Long project_id,

        @NotBlank(message = "리워드 ID는 필수 입력 항목입니다.")
        Long reward_id
) {
}
