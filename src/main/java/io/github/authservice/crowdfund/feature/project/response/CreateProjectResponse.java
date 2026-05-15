package io.github.authservice.crowdfund.feature.project.response;

public record CreateProjectResponse(
        String message,
        Long projectId
) {
}