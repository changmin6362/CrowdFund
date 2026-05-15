package io.github.authservice.crowdfund.feature.project.response;

import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProjectInfo(
        Long projectId,
        String title,
        BigDecimal goalAmount,
        BigDecimal currentAmount,
        LocalDateTime endAt,
        FulfillmentStatus status
) {}
