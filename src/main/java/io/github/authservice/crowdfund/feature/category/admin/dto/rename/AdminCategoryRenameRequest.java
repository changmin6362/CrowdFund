package io.github.authservice.crowdfund.feature.category.admin.dto.rename;

import jakarta.validation.constraints.NotBlank;

// 이름 수정 요청
public record AdminCategoryRenameRequest(
        @NotBlank(message = "이름은 필수 입력값입니다.")
        String name
) {}
