package io.github.authservice.crowdfund.feature.user.response;

public record UserDataInfo(
        String email,
        String nickname,
        String name,
        String phone,
        String role
) {
}
