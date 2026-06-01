package io.github.crowdfund.feature.auth.dto.signup;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthSignUpResponse(
        @Schema(description = "사용자 ID", example = "1")
        Long userId
) {
}
