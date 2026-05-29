package io.github.crowdfund.feature.project.creator;

import io.github.crowdfund.feature.project.creator.dto.create.CreatorProjectCreateRequest;
import io.github.crowdfund.feature.project.creator.dto.update.CreatorProjectUpdateRequest;
import io.github.crowdfund.feature.project.creator.dto.create.CreatorProjectCreateResponse;
import io.github.crowdfund.feature.project.creator.dto.fetch.CreatorProjectsFetchResponse;
import io.github.crowdfund.feature.project.creator.dto.extract.CreatorShippingInfosExtractResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/creator/projects")
@Validated
@RequiredArgsConstructor
@Tag(name = "Project - Creator", description = "창작자용 프로젝트 API")
public class CreatorProjectController {

    private final CreatorProjectService service;

    /**
     * 프로젝트 생성
     *
     * @param request 프로젝트 생성 정보
     * @return message, createdProjectId
     */
    @Operation(summary = "프로젝트 생성")
    @ApiResponse(responseCode = "201", description = "프로젝트 생성 성공")
    @PostMapping("/{creatorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CreatorProjectCreateResponse> create(@PathVariable Long creatorId, @Valid @RequestBody CreatorProjectCreateRequest request) {
        return ApiResult.success("프로젝트 생성에 성공했습니다.", service.create(creatorId, request));
    }

    /**
     * 프로젝트 제목과 본문 수정
     *
     * @param projectId 프로젝트 ID
     * @param request   수정할 프로젝트 정보
     * @return message
     */
    @Operation(summary = "프로젝트 제목과 본문 수정")
    @ApiResponse(responseCode = "200", description = "프로젝트 제목과 본문 수정 성공")
    @PatchMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> update(@PathVariable Long projectId, @Valid @RequestBody CreatorProjectUpdateRequest request) {
        service.update(projectId, request);

        return ApiResult.success("프로젝트 제목과 본문 수정에 성공했습니다.");
    }

    /**
     * 프로젝트 삭제
     *
     * @param projectId 프로젝트 ID
     * @return message
     */
    @Operation(summary = "프로젝트 삭제")
    @ApiResponse(responseCode = "200", description = "프로젝트 삭제 성공")
    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> delete(@PathVariable Long projectId) {
        service.delete(projectId);

        return ApiResult.success("프로젝트 삭제에 성공했습니다.");
    }

    /**
     * 내 프로젝트 조회
     *
     * @param userId 사용자 ID
     * @return message, projects
     */
    @Operation(summary = "내 프로젝트 조회")
    @ApiResponse(responseCode = "200", description = "내 프로젝트 조회 성공")
    @GetMapping("/me/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<CreatorProjectsFetchResponse> fetch(@PathVariable Long userId) {
        return ApiResult.success("내 프로젝트 조회에 성공했습니다.", service.fetch(userId));
    }

    /**
     * 후원자들의 배송 정보 목록 조회
     *
     * @param projectId 프로젝트 ID
     * @return message, shippingInfos
     */
    @Operation(summary = "후원자들의 배송 정보 목록 조회")
    @Schema(description = "배송 정보 목록 조회")
    @GetMapping("/{projectId}/shipping-infos")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<CreatorShippingInfosExtractResponse> extract(@PathVariable Long projectId) {
        return ApiResult.success("배송 정보 조회에 성공했습니다.", service.extract(projectId));
    }
}