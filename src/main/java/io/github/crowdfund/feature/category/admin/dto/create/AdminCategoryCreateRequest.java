package io.github.crowdfund.feature.category.admin.dto.create;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "카테고리 생성 요청")
public record AdminCategoryCreateRequest(
        @Schema(description = "부모 카테고리 ID (최상위 카테고리인 경우 null)", example = "1")
        @Positive(message = "부모 카테고리 ID는 1 이상의 양수여야 합니다.")
        Integer parentId,

        @Schema(description = "카테고리 이름", example = "새 카테고리")
        @NotBlank(message = "카테고리 이름은 필수입니다.")
        @Size(min = 2, max = 20, message = "카테고리 이름은 2자 이상 20자 이하로 입력해주세요.")
        String name
) {
}

