package io.github.crowdfund.global.common.dto.pagination;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * 복합 커서 기반 페이지네이션 공통 요청 레코드
 *
 * @param createdAt: 커서의 날짜 키
 * @param id:        커서의 ID 키
 */
public record CursorRequest(
        @Schema(description = "커서의 날짜 키")
        LocalDateTime createdAt,

        @Schema(description = "커서의 ID 키")
        Long id
) {
    /**
     * 커서 유효성 검증 메서드
     */
    public void validate() {
        if ((createdAt == null) != (id == null)) {
            throw new IllegalArgumentException("createdAt와 id는 함께 전달되어야 하거나, 둘 다 전달되지 않아야 합니다.");
        }
    }
}
