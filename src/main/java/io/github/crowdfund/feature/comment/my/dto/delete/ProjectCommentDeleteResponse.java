package io.github.crowdfund.feature.comment.my.dto.delete;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProjectCommentDeleteResponse(
        @Schema(description = "삭제된 댓글 ID", example = "1")
        Long deletedCommentId
) {
}