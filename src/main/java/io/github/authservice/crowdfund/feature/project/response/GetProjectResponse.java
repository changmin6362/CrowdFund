package io.github.authservice.crowdfund.feature.project.response;

import java.util.List;

public record GetProjectResponse(
        String message,
        List<ProjectInfo> projectList,
        Boolean hasNext
) {
}
