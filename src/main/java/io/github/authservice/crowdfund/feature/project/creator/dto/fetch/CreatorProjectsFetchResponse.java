package io.github.authservice.crowdfund.feature.project.creator.dto.fetch;

import java.util.List;

public record CreatorProjectsFetchResponse(
        List<ProjectInfo> projects
) {
}
