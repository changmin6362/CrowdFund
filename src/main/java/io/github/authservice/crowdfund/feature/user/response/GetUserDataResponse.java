package io.github.authservice.crowdfund.feature.user.response;

public record GetUserDataResponse(
        String message,
        UserDataInfo user
) {
}
