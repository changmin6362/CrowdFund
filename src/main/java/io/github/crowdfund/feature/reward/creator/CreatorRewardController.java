package io.github.crowdfund.feature.reward.creator;

import io.github.crowdfund.feature.reward.creator.dto.create.CreatorRewardCreateRequest;
import io.github.crowdfund.feature.reward.creator.dto.update.CreatorRewardUpdateReqeust;
import io.github.crowdfund.feature.reward.creator.dto.create.CreatorRewardCreateResponse;
import io.github.crowdfund.feature.reward.creator.dto.delete.CreatorRewardDeleteResponse;
import io.github.crowdfund.feature.reward.creator.dto.update.CreatorRewardUpdateResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.github.crowdfund.global.security.SecurityUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/creator")
@RequiredArgsConstructor
@Validated
@Tag(name = "Reward - Creator", description = "창작자용 리워드 API")
public class CreatorRewardController {

    private final CreatorRewardService service;

    /**
     * 프로젝트에 리워드 등록
     *
     * @param projectId 프로젝트 ID
     * @param request   리워드 추가 요청 데이터
     * @return message, createdReward
     */
    @Operation(summary = "프로젝트에 리워드 등록")
    @ApiResponse(responseCode = "201", description = "리워드 등록 성공 응답 예시")
    @PostMapping("/projects/{projectId}/rewards")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CreatorRewardCreateResponse> create(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long projectId,
            @Valid @RequestBody CreatorRewardCreateRequest request) {
        return ApiResult.success("리워드 등록에 성공했습니다.", service.create(securityUser, projectId, request));
    }

    /**
     * 리워드 수정
     *
     * @param rewardId 리워드 ID
     * @param request  리워드 수정 요청 데이터
     * @return message, updatedReward
     */
    @Operation(summary = "리워드 수정")
    @ApiResponse(responseCode = "200", description = "리워드 수정 성공 응답 예시")
    @PatchMapping("/rewards/{rewardId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<CreatorRewardUpdateResponse> update(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable @Valid Long rewardId,
            @Valid @RequestBody CreatorRewardUpdateReqeust request) {
        return ApiResult.success("리워드 수정에 성공했습니다.", service.update(securityUser, rewardId, request));
    }

    /**
     * 리워드 삭제
     *
     * @param rewardId 리워드 ID
     * @return message, deletedRewardId
     */
    @Operation(summary = "리워드 삭제")
    @ApiResponse(responseCode = "200", description = "리워드 삭제 성공 응답 예시")
    @DeleteMapping("/rewards/{rewardId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<CreatorRewardDeleteResponse> delete(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long rewardId) {
        return ApiResult.success("리워드 삭제에 성공했습니다.", service.delete(securityUser, rewardId));
    }
}

