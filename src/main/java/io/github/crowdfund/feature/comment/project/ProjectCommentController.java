package io.github.crowdfund.feature.comment.project;

import io.github.crowdfund.feature.comment.project.dto.create.ProjectCommentCreateRequest;
import io.github.crowdfund.feature.comment.project.dto.update.ProjectCommentUpdateRequest;
import io.github.crowdfund.feature.comment.project.dto.delete.ProjectCommentDeleteResponse;
import io.github.crowdfund.feature.comment.project.dto.fetch.ProjectCommentsFetchResponse;
import io.github.crowdfund.feature.comment.project.dto.create.ProjectCommentCreateResponse;
import io.github.crowdfund.feature.comment.project.dto.update.ProjectCommentUpdateResponse;
import io.github.crowdfund.global.common.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectCommentController {

    private final ProjectCommentService service;

    /**
     * 프로젝트에 댓글 작성
     *
     * @param projectId 프로젝트 아이디
     * @param request   댓글 작성 요청 데이터
     * @return message, createdComment
     */
    @PostMapping("/projects/{projectId}/comments/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<ProjectCommentCreateResponse> create(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @RequestBody @Valid ProjectCommentCreateRequest request) {

        return ApiResult.success("댓글 작성에 성공했습니다.", service.create(projectId, userId, request));
    }

    /**
     * 프로젝트의 댓글 목록 조회
     *
     * @param projectId 프로젝트 아이디
     * @return message, comments
     */
    @GetMapping("/projects/{projectId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<ProjectCommentsFetchResponse> fetch(
            @PathVariable Long projectId,
            @RequestParam(required = false) Long currentUserId) {

        return ApiResult.success("댓글 목록 조회에 성공했습니다.", service.fetch(projectId, currentUserId));
    }

    /**
     * 프로젝트 작성한 댓글 수정
     *
     * @param commentId 댓글 아이디
     * @param request   댓글 수정 요청 데이터
     * @return message, patchedComment
     */
    @PatchMapping("/comments/{commentId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<ProjectCommentUpdateResponse> update(
            @PathVariable Long commentId,
            @PathVariable Long userId,
            @RequestBody @Valid ProjectCommentUpdateRequest request) {

        return ApiResult.success("댓글 수정에 성공했습니다.", service.update(commentId, userId, request));
    }

    /**
     * 프로젝트에 작성한 댓글 삭제
     *
     * @param commentId 댓글 아이디
     * @return message, deletedCommentId
     */
    @DeleteMapping("/comments/{commentId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<ProjectCommentDeleteResponse> delete(
            @PathVariable Long commentId,
            @PathVariable Long userId) {

        return ApiResult.success("내 댓글 삭제에 성공했습니다.", service.delete(commentId, userId));
    }
}