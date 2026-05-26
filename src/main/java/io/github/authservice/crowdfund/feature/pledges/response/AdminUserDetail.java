package io.github.authservice.crowdfund.feature.pledges.response;

public record AdminUserDetail(
        Long userId,
        String name,
        String nickname,
        String email,
        String phone
) {
}
