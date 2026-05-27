package io.github.crowdfund.feature.project.user.dto.fetch;

import io.github.crowdfund.domain.project.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProjectElement(
        Long projectId,
        Long creatorId,
        Integer categoryId,
        String title,
        BigDecimal goalAmount,
        BigDecimal currentAmount,
        LocalDateTime endAt,
        ProjectStatus status,
        LocalDateTime createdAt
) {
}
