package io.github.crowdfund.feature.project.user.dto.fetch;

import java.util.List;

public record UserProjectFetchResponse(
        List<ProjectElement> projectList,
        Boolean hasNext,
        NextCursor nextCursor
) {
}
