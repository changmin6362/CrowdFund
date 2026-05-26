package io.github.authservice.crowdfund.feature.auth.response;

public record SignInResponse(
        String accessToken,
        String refreshToken
) {
}
