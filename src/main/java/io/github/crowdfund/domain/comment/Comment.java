package io.github.crowdfund.domain.comment;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

/**
 * 댓글 테이블 매핑용 엔티티
 *
 * @param id        댓글 ID
 * @param userId    댓글 단 사용자 ID
 * @param projectId 프로젝트 ID
 * @param content   댓글 내용
 * @param createdAt 작성일시
 */
@Table("comment")
public record Comment(
    @Id Long id,
    Long userId,
    Long projectId,
    String content,
    LocalDateTime createdAt
) {}
