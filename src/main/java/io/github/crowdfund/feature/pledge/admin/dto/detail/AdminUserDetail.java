package io.github.crowdfund.feature.pledge.admin.dto.detail;

public record AdminUserDetail(
        Long userId,
        String name,
        String nickname,
        String email,
        String phone
) {
}
