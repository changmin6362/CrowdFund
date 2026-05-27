package io.github.authservice.crowdfund.feature.comment.project.dto.update;

import jakarta.validation.constraints.NotBlank;

public record ProjectCommentUpdateRequest(

        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content

) {
}
