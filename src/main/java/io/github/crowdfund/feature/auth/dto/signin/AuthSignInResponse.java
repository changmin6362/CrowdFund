package io.github.crowdfund.feature.auth.dto.signin;

public record AuthSignInResponse(
        String accessToken,
        String refreshToken
) {
}
