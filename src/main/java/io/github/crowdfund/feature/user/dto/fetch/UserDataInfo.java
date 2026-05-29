package io.github.crowdfund.feature.user.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserDataInfo(
        @Schema(description = "유저 이메일")
        String email,

        @Schema(description = "유저 닉네임")
        String nickname,

        @Schema(description = "유저 이름")
        String name,

        @Schema(description = "유저 전화번호")
        String phone,

        @Schema(description = "유저 역할")
        String role
) {
}
