package io.github.crowdfund.feature.pledges.admin.dto.detail;

import java.math.BigDecimal;

public record AdminPaymentDetail(
        BigDecimal amount,
        String paymentMethod
) {
}
