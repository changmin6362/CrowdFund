package io.github.authservice.crowdfund.feature.project.response;

import io.github.authservice.crowdfund.domain.project.ProjectStatus;

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
