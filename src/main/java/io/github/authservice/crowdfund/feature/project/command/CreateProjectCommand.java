package io.github.authservice.crowdfund.feature.project.command;

import io.github.authservice.crowdfund.domain.project.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateProjectCommand(
        Integer categoryId,
        String title,
        String contentBlocks,
        BigDecimal goalAmount,
        BigDecimal currentAmount,
        LocalDateTime endAt,
        ProjectStatus status
) {
}
