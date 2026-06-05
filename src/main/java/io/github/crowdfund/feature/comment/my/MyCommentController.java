package io.github.crowdfund.feature.comment.my;

import io.github.crowdfund.feature.comment.my.dto.delete.ProjectCommentDeleteResponse;
import io.github.crowdfund.feature.comment.my.dto.fetch.MyCommentsResponse;
import io.github.crowdfund.feature.comment.my.dto.update.ProjectCommentUpdateRequest;
import io.github.crowdfund.feature.comment.my.dto.update.ProjectCommentUpdateResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.github.crowdfund.global.security.SecurityUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me/comments")
@RequiredArgsConstructor
@Validated
@Tag(name = "10. Comment - My", description = "내 댓글 API")
public class MyCommentController {

    private final MyCommentService service;

    /**
     * 내 댓글 목록 조회 (복합 커서 기반 최신순 페이지네이션)
     *
     * @return message, myComments
     */
    @Operation(summary = "내 댓글 목록 조회")
    @ApiResponse(responseCode = "200", description = "내 댓글 목록 조회 성공 응답 예시")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<MyCommentsResponse> fetch(
            @AuthenticationPrincipal SecurityUser securityUser,
            @ParameterObject CursorRequest cursorRequest,
            @RequestParam(defaultValue = "10") @Positive Integer limit) {

        return ApiResult.success("내 댓글 목록 조회에 성공했습니다.", service.fetch(securityUser.getUserId(), cursorRequest, limit));
    }

    /**
     * 내 댓글 수정
     *
     * @param commentId 댓글 ID
     * @param request   댓글 수정 요청 데이터
     * @return message, patchedComment
     */
    @Operation(summary = "내 댓글 수정")
    @ApiResponse(responseCode = "200", description = "댓글 수정 성공 응답 예시")
    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<ProjectCommentUpdateResponse> update(
            @PathVariable Long commentId,
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @RequestBody ProjectCommentUpdateRequest request) {

        return ApiResult.success("댓글 수정에 성공했습니다.", service.update(commentId, securityUser, request));
    }

    /**
     * 내 댓글 삭제
     *
     * @param commentId 댓글 ID
     * @return message, deletedCommentId
     */
    @Operation(summary = "내 댓글 삭제")
    @ApiResponse(responseCode = "200", description = "댓글 삭제 성공 응답 예시")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<ProjectCommentDeleteResponse> delete(
            @PathVariable Long commentId,
            @AuthenticationPrincipal SecurityUser securityUser) {

        return ApiResult.success("내 댓글 삭제에 성공했습니다.", service.delete(commentId, securityUser));
    }
}
