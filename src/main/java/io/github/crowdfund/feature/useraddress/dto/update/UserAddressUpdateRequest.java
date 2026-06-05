package io.github.crowdfund.feature.useraddress.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserAddressUpdateRequest(
        @Schema(description = "수령인 이름", example = "김공자")
        @NotBlank(message = "수령인 이름은 필수 입력 항목입니다.")
        String recipientName,

        @Schema(description = "전화번호", example = "010-1234-5678")
        @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "유효한 전화번호 형식이 아닙니다. (예: 010-1234-5678)")
        String phone,

        @Schema(description = "우편번호", example = "12345")
        @NotBlank(message = "우편번호는 필수 입력 항목입니다.")
        String postalCode,

        @Schema(description = "기본 주소", example = "서울시 중구")
        @NotBlank(message = "기본 주소는 필수 입력 항목입니다.")
        String addressMain,

        @Schema(description = "상세 주소", example = "관철동 123-45")
        @NotBlank(message = "상세 주소는 필수 입력 항목입니다.")
        String addressDetail
) {
}
