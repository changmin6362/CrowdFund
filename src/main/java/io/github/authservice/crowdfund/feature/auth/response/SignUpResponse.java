package io.github.authservice.crowdfund.feature.auth.response;

public record SignUpResponse(
        String message,
        Integer userId
) {
}
