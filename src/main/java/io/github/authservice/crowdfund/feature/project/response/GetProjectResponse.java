package io.github.authservice.crowdfund.feature.project.response;

import java.util.List;

public record GetProjectResponse(
        List<ProjectElement> projectList,
        Boolean hasNext,
        NextCursor nextCursor
) {
}
