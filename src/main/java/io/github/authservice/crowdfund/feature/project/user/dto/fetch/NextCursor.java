package io.github.authservice.crowdfund.feature.project.user.dto.fetch;

import java.time.LocalDateTime;

public record NextCursor(
        LocalDateTime createdAt,
        Long id
) {}
