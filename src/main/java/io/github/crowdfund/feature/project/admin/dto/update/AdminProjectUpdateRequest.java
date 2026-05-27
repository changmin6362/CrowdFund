package io.github.crowdfund.feature.project.admin.dto.update;

import io.github.crowdfund.domain.project.ProjectStatus;
import jakarta.validation.constraints.NotNull;

/**
 * 프로젝트 상태 변경 요청 객체.
 * Record 타입을 활용하여 불필요한 Getter 선언을 제거함.
 */
public record AdminProjectUpdateRequest(
        @NotNull(message = "변경할 상태값은 필수입니다.")
        ProjectStatus status
) {
}