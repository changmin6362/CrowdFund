package io.github.crowdfund.feature.payment.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentCreateRequest(
        @Schema(description = "후원 ID")
        @NotNull(message = "후원 ID는 필수입니다.")
        Long pledgeId,

        @Schema(description = "결제 수단")
        @NotBlank(message = "결제 수단은 필수입니다.")
        String paymentMethod,

        @Schema(description = "결제 금액")
        @NotNull(message = "결제 금액은 필수입니다.")
        @Positive(message = "결제 금액은 0보다 커야 합니다.")
        Long amount
) {
}
