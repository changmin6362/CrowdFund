package io.github.authservice.crowdfund.feature.auth.request;

public record SignUpRequest(
        String email,
        String password,
        String nickName,
        String name,
        String phone
) {
}
