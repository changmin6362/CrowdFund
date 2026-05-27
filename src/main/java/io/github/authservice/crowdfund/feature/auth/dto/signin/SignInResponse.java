package io.github.authservice.crowdfund.feature.auth.dto.signin;

public record SignInResponse(
        String accessToken,
        String refreshToken
) {
}
