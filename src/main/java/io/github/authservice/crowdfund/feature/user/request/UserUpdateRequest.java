package io.github.authservice.crowdfund.feature.user.request;

public record UserUpdateRequest(
        String nickname,
        String name,
        String phone
) {
}
