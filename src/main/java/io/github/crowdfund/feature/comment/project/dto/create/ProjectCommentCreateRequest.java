package io.github.crowdfund.feature.comment.project.dto.create;

import jakarta.validation.constraints.NotBlank;

public record ProjectCommentCreateRequest(

        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content

) {
}
