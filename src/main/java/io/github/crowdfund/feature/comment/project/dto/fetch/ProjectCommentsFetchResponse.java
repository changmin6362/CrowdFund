package io.github.crowdfund.feature.comment.project.dto.fetch;

import io.github.crowdfund.feature.comment.project.dto.CommentInfo;
import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ProjectCommentsFetchResponse(
        @Schema(description = "프로젝트 댓글 목록")
        List<CommentInfo> comments,

        @Schema(description = "다음 페이지 존재 여부", example = "true")
        boolean hasNext,

        @Schema(description = "다음 페이지 커서 정보")
        CursorRequest nextCursor
) {
}