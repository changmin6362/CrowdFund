package io.github.crowdfund.feature.auth.dto.signin;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthSignInResponse(
        @Schema(description = "액세스 토큰", example = "액세스 토큰 문자열")
        String accessToken,

        @Schema(description = "리프레시 토큰", example = "리프레시 토큰 문자열")
        String refreshToken,

        @Schema(description = "사용자 정보")
        UserSigninInfo userInfo
) {
}
