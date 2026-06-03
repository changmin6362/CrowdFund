package io.github.crowdfund.feature.auth.dto.login;

import io.github.crowdfund.feature.user.dto.fetch.UserDataInfo;

public record LoginResponse(
    String accessToken,
    String tokenType,
    UserDataInfo userInfo
) {
    public LoginResponse(String accessToken, UserDataInfo userInfo) {
        this(accessToken, "Bearer", userInfo);
    }
}
