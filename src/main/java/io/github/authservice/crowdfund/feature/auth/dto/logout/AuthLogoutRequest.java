package io.github.authservice.crowdfund.feature.auth.dto.logout;

import jakarta.validation.constraints.NotBlank;

public record AuthLogoutRequest(
        @NotBlank(message = "리프레시 토큰은 필수입니다.")
        String refreshToken
) {
}
