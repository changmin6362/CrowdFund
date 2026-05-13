package io.github.authservice.crowdfund.feature.comment.request;

import jakarta.validation.constraints.NotBlank;

/**
 * 댓글 수정 요청 데이터
 *
 * content : 댓글 내용
 */
public record CommentModifyRequest(

        @NotBlank(message = "댓글이 존재해야 합니다.")
        String content

) {
}
