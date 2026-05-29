package io.github.crowdfund.feature.comment.project.dto.create;

import io.github.crowdfund.feature.comment.project.dto.CommentInfo;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProjectCommentCreateResponse(
        @Schema(description = "생성된 댓글 정보")
        CommentInfo createdComment
) {
}