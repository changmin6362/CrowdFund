package io.github.crowdfund.feature.comment.project;

import io.github.crowdfund.feature.comment.project.dto.create.ProjectCommentCreateRequest;
import io.github.crowdfund.feature.comment.project.dto.create.ProjectCommentCreateResponse;
import io.github.crowdfund.feature.comment.project.dto.fetch.ProjectCommentsFetchResponse;
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
    @PostMapping("/projects/{projectId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<ProjectCommentCreateResponse> create(
            @PathVariable Long projectId,
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @RequestBody ProjectCommentCreateRequest request) {

        return ApiResult.success("댓글 작성에 성공했습니다.", service.create(projectId, securityUser.getUserId(), request));
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
}