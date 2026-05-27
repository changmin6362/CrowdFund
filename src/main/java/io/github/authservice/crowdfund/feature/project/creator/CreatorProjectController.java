package io.github.authservice.crowdfund.feature.project.creator;

import io.github.authservice.crowdfund.feature.project.creator.dto.create.CreatorProjectCreateRequest;
import io.github.authservice.crowdfund.feature.project.creator.dto.update.CreatorProjectUpdateRequest;
import io.github.authservice.crowdfund.feature.project.creator.dto.create.CreatorProjectCreateResponse;
import io.github.authservice.crowdfund.feature.project.creator.dto.fetch.CreatorProjectsFetchResponse;
import io.github.authservice.crowdfund.feature.project.creator.dto.extract.CreatorShippingInfosExtractResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Project", description = "프로젝트 관련 API")
@RestController
@RequestMapping("/api")
@Validated
@RequiredArgsConstructor
public class CreatorProjectController {

    private final CreatorProjectService service;

    /**
     * 프로젝트 생성
     *
     * @param request 프로젝트 생성 정보
     * @return message, projectId
     */
    @PostMapping("/projects/{creatorId}")
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
    @PatchMapping("/projects/{projectId}")
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
    @DeleteMapping("/projects/{projectId}")
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
    @GetMapping("/users/me/projects/{userId}")
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
    @GetMapping("/projects/{projectId}/shipping-infos")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<CreatorShippingInfosExtractResponse> extract(@PathVariable Long projectId) {
        return ApiResult.success("배송 정보 조회에 성공했습니다.", service.extract(projectId));
    }
}