package io.github.authservice.crowdfund.feature.project.response;

import java.util.List;

public record GetMyProjectsResponse(
        String message,
        List<ProjectInfo> projectList
) {
}
