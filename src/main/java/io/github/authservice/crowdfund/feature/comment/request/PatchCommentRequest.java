package io.github.authservice.crowdfund.feature.comment.request;

import jakarta.validation.constraints.NotBlank;

public record PatchCommentRequest(

        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content

) {
}
