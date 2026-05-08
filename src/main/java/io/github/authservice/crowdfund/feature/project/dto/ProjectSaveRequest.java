package io.github.authservice.crowdfund.feature.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * 프로젝트 데이터 영속화 및 요청 매핑용 객체.
 *
 * @Id를 통한 식별자 매핑 및 @NotBlank 등을 통한 데이터 유효성 검증 수행.
 */
public record ProjectSaveRequest(
        @Id
        Long id,

        @NotBlank(message = "프로젝트 제목은 필수임")
        String title,

        @NotBlank(message = "프로젝트 설명은 필수임")
        String description,

        @NotNull(message = "목표 금액은 필수임")
        Long goalAmount,

        LocalDateTime startAt,
        LocalDateTime endAt,
        Long categoryId,
        Long creatorId
) {
}