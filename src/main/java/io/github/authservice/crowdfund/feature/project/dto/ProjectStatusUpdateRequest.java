package io.github.authservice.crowdfund.feature.project.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 프로젝트 상태 변경 요청 객체.
 * Record 타입을 활용하여 불필요한 Getter 선언을 제거함.
 */
public record ProjectStatusUpdateRequest(
        @NotBlank(message = "변경할 상태값은 필수입니다.")
        String status
) {
}