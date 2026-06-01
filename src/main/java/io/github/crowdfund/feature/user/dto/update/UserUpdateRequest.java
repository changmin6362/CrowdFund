package io.github.crowdfund.feature.user.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(
        @Schema(description = "닉네임", example = "닉네임 예시")
        @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
        String nickname,

        @Schema(description = "이름", example = "이름 예시")
        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        String name,

        @Schema(description = "전화번호", example = "010-1234-5678")
        @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "유효한 전화번호 형식이 아닙니다. (예: 010-1234-5678)")
        String phone
) {
}
