package io.github.crowdfund.feature.comment.project.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ProjectCommentCreateRequest(
        @Schema(description = "댓글 내용", example = "댓글 내용 예시")
        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content

) {
}
