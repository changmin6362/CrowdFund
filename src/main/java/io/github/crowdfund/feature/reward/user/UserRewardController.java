package io.github.crowdfund.feature.reward.user;

import io.github.crowdfund.feature.reward.user.dto.fetch.UserRewardsFetchResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Reward - User", description = "사용자용 리워드 API")
public class UserRewardController {

    private final UserRewardService service;

    /**
     * 프로젝트의 리워드 목록 조회
     *
     * @param projectId 프로젝트아이디
     * @return message, rewards
     */
    @Operation(summary = "프로젝트의 리워드 목록 조회")
    @ApiResponse(responseCode = "200", description = "리워드 목록 조회 성공")
    @GetMapping("/projects/{projectId}/rewards")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserRewardsFetchResponse> fetch(@PathVariable Long projectId) {
        return ApiResult.success("리워드 목록 조회에 성공했습니다.", service.fetch(projectId));
    }
}

