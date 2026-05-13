package io.github.authservice.crowdfund.feature.project.response;

public record GetProjectsByUserResponse(
        String message,
        ProjectInfo project
) {
}
