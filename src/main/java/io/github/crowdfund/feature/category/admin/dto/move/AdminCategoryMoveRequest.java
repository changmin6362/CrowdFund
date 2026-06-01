package io.github.crowdfund.feature.category.admin.dto.move;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

@Schema(description = "카테고리 이동(부모 변경) 요청")
public record AdminCategoryMoveRequest(
        @Schema(description = "부모 카테고리 ID (최상위로 이동 시 null)", example = "1")
        @Positive(message = "상위 카테고리 ID는 양수여야 합니다.")
        Integer parentId
) {
}
