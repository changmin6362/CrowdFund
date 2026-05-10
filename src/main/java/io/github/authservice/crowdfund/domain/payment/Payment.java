package io.github.authservice.crowdfund.domain.payment;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("payment")
public record Payment(
    @Id Long id,
    Long pledgeId,
    String paymentMethod,
    Long amount,
    String status,
    LocalDateTime paidAt
) {}
