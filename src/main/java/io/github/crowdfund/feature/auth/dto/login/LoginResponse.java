package io.github.crowdfund.feature.auth.dto.login;

public record LoginResponse(
        String accessToken,
        String tokenType,
        UserProfileInfo userInfo
) {
    public LoginResponse(String accessToken, UserProfileInfo userInfo) {
        this(accessToken, "Bearer", userInfo);
    }
}
