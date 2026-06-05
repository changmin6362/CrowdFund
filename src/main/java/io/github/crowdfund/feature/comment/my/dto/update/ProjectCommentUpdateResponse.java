package io.github.crowdfund.feature.comment.my.dto.update;

import io.github.crowdfund.feature.comment.project.dto.CommentInfo;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProjectCommentUpdateResponse(
        @Schema(description = "수정된 댓글 정보")
        CommentInfo patchedComment
) {
}