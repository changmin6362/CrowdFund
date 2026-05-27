package io.github.authservice.crowdfund.feature.pledges.admin.dto.detail;

public record AdminUserDetail(
        Long userId,
        String name,
        String nickname,
        String email,
        String phone
) {
}
