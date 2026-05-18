package io.github.authservice.crowdfund.feature.project.response;

import io.github.authservice.crowdfund.domain.project.ProjectStatus;

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
