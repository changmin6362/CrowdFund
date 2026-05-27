package io.github.crowdfund.feature.project.user.dto.fetch;

import java.time.LocalDateTime;

public record NextCursor(
        LocalDateTime createdAt,
        Long id
) {}
