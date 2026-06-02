package io.github.crowdfund.domain.comment.mapper;

import io.github.crowdfund.feature.comment.project.dto.CommentInfo;
import io.github.crowdfund.feature.comment.my.dto.fetch.MyCommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {

    void update(@Param("commentId") Long commentId, @Param("content") String content);

    List<CommentInfo> findAllByProjectId(
            @Param("projectId") Long projectId,
            @Param("currentUserId") Long currentUserId,
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorId") Long cursorId,
            @Param("limit") Integer limit
    );

    List<MyCommentInfo> findAllByUserId(
            @Param("userId") Long userId,
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorId") Long cursorId,
            @Param("limit") Integer limit
    );

    Optional<CommentInfo> findByIdToCommentInfo(@Param("commentId") Long commentId, @Param("currentUserId") Long currentUserId);
}
