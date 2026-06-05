package io.github.crowdfund.feature.auth.dto.signup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @Schema(description = "이메일", example = "sample@test.com")
        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @Size(max = 50)
        String email,

        @Schema(description = "비밀번호", example = "aw123456!")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
                message = "비밀번호는 8자 이상, 영문, 숫자, 특수문자를 포함해야 합니다.")
        String password,

        @Schema(description = "닉네임", example = "차분한 사용자123")
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(max = 12)
        String nickname,

        @Schema(description = "이름", example = "김공자")
        @NotBlank(message = "이름을 입력해주세요.")
        @Size(max = 6)
        String name,

        @Schema(description = "전화번호", example = "010-1234-5678")
        @NotBlank(message = "전화번호를 입력해주세요.")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
        String phone
) {
}
