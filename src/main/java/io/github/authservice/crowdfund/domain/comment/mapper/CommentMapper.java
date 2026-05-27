package io.github.authservice.crowdfund.domain.comment.mapper;

import io.github.authservice.crowdfund.feature.comment.project.dto.CommentInfo;
import io.github.authservice.crowdfund.feature.comment.my.dto.fetch.MyCommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {

    void update(@Param("commentId") Long commentId, @Param("content") String content);

    List<CommentInfo> findAllByProjectId(@Param("projectId") Long projectId, @Param("currentUserId") Long currentUserId);

    List<MyCommentInfo> findAllByUserId(Long userId);

    Optional<CommentInfo> findByIdToCommentInfo(@Param("commentId") Long commentId, @Param("currentUserId") Long currentUserId);
}
