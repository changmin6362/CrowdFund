package io.github.crowdfund.feature.payment.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentCreateRequest(
        @NotNull(message = "후원 ID는 필수입니다.")
        Long pledgeId,

        @NotBlank(message = "결제 수단은 필수입니다.")
        String paymentMethod,

        @NotNull(message = "결제 금액은 필수입니다.")
        @Positive(message = "결제 금액은 0보다 커야 합니다.")
        Long amount
) {
}
