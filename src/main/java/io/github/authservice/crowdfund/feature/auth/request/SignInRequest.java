package io.github.authservice.crowdfund.feature.auth.request;

public record SignInRequest(
        String email,
        String password
) {
}
