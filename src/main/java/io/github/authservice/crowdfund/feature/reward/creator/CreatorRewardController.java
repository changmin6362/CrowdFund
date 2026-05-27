package io.github.authservice.crowdfund.feature.reward.creator;

import io.github.authservice.crowdfund.feature.reward.creator.dto.create.CreatorRewardCreateRequest;
import io.github.authservice.crowdfund.feature.reward.creator.dto.update.CreatorRewardUpdateReqeust;
import io.github.authservice.crowdfund.feature.reward.creator.dto.create.CreatorRewardCreateResponse;
import io.github.authservice.crowdfund.feature.reward.creator.dto.delete.CreatorRewardDeleteResponse;
import io.github.authservice.crowdfund.feature.reward.creator.dto.update.CreatorRewardUpdateResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CreatorRewardController {

    private final CreatorRewardService service;

    /**
     * 프로젝트에 리워드 등록
     *
     * @param projectId 프로젝트아이디
     * @param request   리워드 추가 요청 데이터
     * @return message, createdReward
     */
    @PostMapping("/projects/{projectId}/rewards")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CreatorRewardCreateResponse> create(@Valid @PathVariable Long projectId, @RequestBody CreatorRewardCreateRequest request) {
        return ApiResult.success("리워드 등록에 성공했습니다.", service.create(projectId, request));
    }

    /**
     * 리워드 수정
     *
     * @param rewardId 프로젝트아이디
     * @param request  리워드 수정 요청 데이터
     * @return message, patchedReward
     */
    @PatchMapping("/rewards/{rewardId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<CreatorRewardUpdateResponse> update(@PathVariable @Valid Long rewardId, @RequestBody CreatorRewardUpdateReqeust request) {
        return ApiResult.success("리워드 수정에 성공했습니다.", service.update(rewardId, request));
    }

    /**
     * 리워드 삭제
     *
     * @param rewardId 프로젝트아이디
     * @return message, deletedRewardId
     */
    @DeleteMapping("/rewards/{rewardId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<CreatorRewardDeleteResponse> delete(@PathVariable Long rewardId) {
        return ApiResult.success("리워드 삭제에 성공했습니다.", service.delete(rewardId));
    }
}

