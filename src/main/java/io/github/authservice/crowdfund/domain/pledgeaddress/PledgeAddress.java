package io.github.authservice.crowdfund.domain.pledgeaddress;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("pledge_address")
public record PledgeAddress(
    @Id Long id,
    Long pledgeId,
    Long userId,
    String recipientName,
    String phone,
    String postalCode,
    String addressMain
) {}
