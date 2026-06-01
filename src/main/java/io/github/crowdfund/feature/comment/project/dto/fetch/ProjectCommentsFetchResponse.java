package io.github.crowdfund.feature.comment.project.dto.fetch;

import io.github.crowdfund.feature.comment.project.dto.CommentInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ProjectCommentsFetchResponse(
        @Schema(description = "프로젝트 댓글 목록")
        List<CommentInfo> comments
) {
}