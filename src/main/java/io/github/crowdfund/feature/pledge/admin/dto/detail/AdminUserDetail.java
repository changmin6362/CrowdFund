package io.github.crowdfund.feature.pledge.admin.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;

public record AdminUserDetail(
        @Schema(description = "유저 ID", example = "1")
        Long userId,

        @Schema(description = "유저 이름", example = "김공자")
        String name,

        @Schema(description = "유저 닉네임", example = "닉네임 예시")
        String nickname,

        @Schema(description = "유저 이메일", example = "example@example.com")
        String email,

        @Schema(description = "유저 전화번호", example = "010-1234-5678")
        String phone
) {
}
