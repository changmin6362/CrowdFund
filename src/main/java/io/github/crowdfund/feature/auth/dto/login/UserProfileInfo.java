package io.github.crowdfund.feature.auth.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserProfileInfo(
        @Schema(description = "유저 이메일", example = "example@text.com")
        String email,

        @Schema(description = "유저 닉네임", example = "유저 닉네임 예시")
        String nickname
) {
}
