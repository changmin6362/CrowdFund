package io.github.crowdfund.feature.user.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserFetchResponse(
        @Schema(description = "유저 정보")
        UserDataInfo user
) {
}
