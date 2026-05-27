package io.github.crowdfund.feature.user.dto.fetch;

public record UserDataInfo(
        String email,
        String nickname,
        String name,
        String phone,
        String role
) {
}
