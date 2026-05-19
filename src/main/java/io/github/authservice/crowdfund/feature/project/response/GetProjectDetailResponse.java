package io.github.authservice.crowdfund.feature.project.response;

public record GetProjectDetailResponse(
        String message,
        ProjectDetail projectDetail
) {

}