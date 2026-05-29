package io.github.crowdfund.feature.comment.project.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ProjectCommentUpdateRequest(
        @Schema(description = "댓글 내용")
        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content

) {
}
