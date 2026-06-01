package io.github.crowdfund.feature.auth.dto.signin;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserSigninInfo(
        @Schema(description = "사용자 ID", example = "1")
        Long userId,

        @Schema(description = "사용자 이메일", example = "example@gmail.com")
        String email,

        @Schema(description = "닉네임", example = "닉네임 문자열")
        String nickname,

        @Schema(description = "역할", example = "ROLE_USER")
        String role
        ) {
}
