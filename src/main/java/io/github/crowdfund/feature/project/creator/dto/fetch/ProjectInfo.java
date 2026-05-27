package io.github.crowdfund.feature.project.creator.dto.fetch;

import io.github.crowdfund.domain.project.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProjectInfo(
        Long projectId,
        String title,
        BigDecimal goalAmount,
        BigDecimal currentAmount,
        LocalDateTime endAt,
        ProjectStatus status
) {}
