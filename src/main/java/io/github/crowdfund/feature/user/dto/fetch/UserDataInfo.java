package io.github.crowdfund.feature.user.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserDataInfo(
        @Schema(description = "유저 이메일", example = "example@text.com")
        String email,

        @Schema(description = "유저 닉네임", example = "유저 닉네임 예시")
        String nickname,

        @Schema(description = "유저 이름", example = "유저 이름 예시")
        String name,

        @Schema(description = "유저 전화번호", example = "010-1234-5678")
        String phone,

        @Schema(description = "역할", example = "USER")
        String role
) {
}
