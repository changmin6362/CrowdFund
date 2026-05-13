package io.github.authservice.crowdfund.feature.auth.response;

public record SignInResponse(
        String message,
        String accessToken,
        String refreshToken
) {
}
