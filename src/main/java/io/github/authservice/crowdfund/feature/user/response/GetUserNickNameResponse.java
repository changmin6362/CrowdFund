package io.github.authservice.crowdfund.feature.user.response;

public record GetUserNickNameResponse(
        String message,
        String nickname
) {
}
