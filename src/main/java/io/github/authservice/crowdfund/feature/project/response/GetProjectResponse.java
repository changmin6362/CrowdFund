package io.github.authservice.crowdfund.feature.project.response;

public record GetProjectResponse(
        String message,
        ProjectInfo projects
) {
}
