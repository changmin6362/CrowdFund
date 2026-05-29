package io.github.crowdfund.feature.category.admin.dto.rename;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "카테고리 이름 변경 요청")
public record AdminCategoryRenameRequest(
        @Schema(description = "변경할 카테고리 이름", example = "변경된 카테고리명")
        @NotBlank(message = "이름은 필수 입력값입니다.")
        String name
) {}
