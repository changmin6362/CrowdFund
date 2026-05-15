package io.github.authservice.crowdfund.domain.project;

import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 프로젝트 테이블 매핑용 엔티티
 *
 * @param id             프로젝트 ID
 * @param categoryId     카테고리 ID
 * @param creatorId      생성자 ID
 * @param title          프로젝트 제목 (최대 30자)
 * @param contentBlocks    프로젝트 설명
 * @param goalAmount     목표 금액
 * @param currentAmount  현재 모금액
 * @param endAt          종료일
 * @param status         프로젝트 상태 [READY: 펀딩 대기중, ONGOING: 펀딩 진행중, COMPLETED: 펀딩 기간 종료, CANCELED: 펀딩 취소됨]
 * @param createdAt      생성일시
 */
@Table("project")
@Alias("Project")
public record Project(
    @Id Long id,
    Integer categoryId,
    Long creatorId,
    String title,
    String contentBlocks,
    BigDecimal goalAmount,
    BigDecimal currentAmount,
    LocalDateTime endAt,
    ProjectStatus status,
    LocalDateTime createdAt
) {}
