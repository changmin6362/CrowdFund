package io.github.authservice.crowdfund.feature.project.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 프로젝트 상태 변경 요청 객체.
 */
public record ProjectStatusUpdateRequest(
        @NotBlank(message = "변경할 상태값은 필수입니다.")
        String status
) {
    // 컨트롤러에서 request.getStatus()를 쓰기 위한 메서드
    public String getStatus() {
        return status;
    }
}