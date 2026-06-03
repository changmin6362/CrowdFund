package io.github.crowdfund.feature.comment.project;

import io.github.crowdfund.feature.comment.project.dto.create.ProjectCommentCreateRequest;
import io.github.crowdfund.feature.comment.project.dto.create.ProjectCommentCreateResponse;
import io.github.crowdfund.feature.comment.project.dto.delete.ProjectCommentDeleteResponse;
import io.github.crowdfund.feature.comment.project.dto.fetch.ProjectCommentsFetchResponse;
import io.github.crowdfund.feature.comment.project.dto.update.ProjectCommentUpdateRequest;
import io.github.crowdfund.feature.comment.project.dto.update.ProjectCommentUpdateResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Tag(name = "Comment - Project", description = "프로젝트의 댓글 API")
public class ProjectCommentController {

    private final ProjectCommentService service;

    /**
     * 프로젝트에 댓글 작성
     *
     * @param projectId 프로젝트 ID
     * @param request   댓글 작성 요청 데이터
     * @return message, createdComment
     */
    @Operation(summary = "프로젝트에 댓글 작성")
    @ApiResponse(responseCode = "201", description = "댓글 작성 성공 응답 예시")
    @PostMapping("/projects/{projectId}/comments/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<ProjectCommentCreateResponse> create(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @Valid @RequestBody ProjectCommentCreateRequest request) {

        return ApiResult.success("댓글 작성에 성공했습니다.", service.create(projectId, userId, request));
    }

    /**
     * 프로젝트의 댓글 목록 조회 (복합 커서 기반 최신순 페이지네이션)
     *
     * @param projectId 프로젝트 ID
     * @return message, comments, hasNext, nextCursor
     */
    @Operation(summary = "프로젝트의 댓글 목록 조회")
    @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공 응답 예시")
    @GetMapping("/projects/{projectId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<ProjectCommentsFetchResponse> fetch(
            @PathVariable Long projectId,
            @RequestParam(required = false) Long currentUserId,
            @ParameterObject CursorRequest cursorRequest,
            @RequestParam(defaultValue = "10") @Positive Integer limit) {

        return ApiResult.success("댓글 목록 조회에 성공했습니다.", service.fetch(projectId, currentUserId, cursorRequest, limit));
    }

    /**
     * 프로젝트 작성한 댓글 수정
     *
     * @param commentId 댓글 ID
     * @param request   댓글 수정 요청 데이터
     * @return message, patchedComment
     */
    @Operation(summary = "프로젝트 작성한 댓글 수정")
    @ApiResponse(responseCode = "200", description = "댓글 수정 성공 응답 예시")
    @PatchMapping("/comments/{commentId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<ProjectCommentUpdateResponse> update(
            @PathVariable Long commentId,
            @PathVariable Long userId,
            @Valid @RequestBody ProjectCommentUpdateRequest request) {

        return ApiResult.success("댓글 수정에 성공했습니다.", service.update(commentId, userId, request));
    }

    /**
     * 프로젝트에 작성한 댓글 삭제
     *
     * @param commentId 댓글 ID
     * @return message, deletedCommentId
     */
    @Operation(summary = "프로젝트에 작성한 댓글 삭제")
    @ApiResponse(responseCode = "200", description = "댓글 삭제 성공 응답 예시")
    @DeleteMapping("/comments/{commentId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<ProjectCommentDeleteResponse> delete(
            @PathVariable Long commentId,
            @PathVariable Long userId) {

        return ApiResult.success("내 댓글 삭제에 성공했습니다.", service.delete(commentId, userId));
    }
}