package io.github.crowdfund.feature.user.dto.view;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserViewResponse(
        @Schema(description = "유저 닉네임")
        String nickname
) {
}
