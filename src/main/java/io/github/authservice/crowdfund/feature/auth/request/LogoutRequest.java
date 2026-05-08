package io.github.authservice.crowdfund.feature.auth.request;

public record LogoutRequest(
        String refreshToken
) {
}
