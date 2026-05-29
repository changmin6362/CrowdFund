package io.github.crowdfund.feature.pledges.user;

import io.github.crowdfund.feature.pledges.user.dto.create.UserPledgeCreateRequest;
import io.github.crowdfund.feature.pledges.user.dto.create.UserPledgeCreateResponse;
import io.github.crowdfund.feature.pledges.user.dto.detail.UserPledgeDetailResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pledges")
@RequiredArgsConstructor
public class UserPledgeController {

    private final UserPledgeService service;

    /**
     * 프로젝트 후원하기
     *
     * @param userId  유저 아이디
     * @param request 후원 정보
     * @return message, pledgeId
     */
    @Operation(summary = "프로젝트 후원하기")
    @ApiResponse(responseCode = "201", description = "프로젝트 후원 성공")
    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<UserPledgeCreateResponse> create(
            @PathVariable Long userId,
            @Valid @RequestBody UserPledgeCreateRequest request) {
        return ApiResult.success("프로젝트 후원에 성공했습니다.", service.create(userId, request));
    }

    /**
     * 후원 상세 조회
     *
     * @param pledgeId 해당 펀딩 아이디
     * @return message, pledgeDetail
     */
    @Operation(summary = "후원 상세 조회")
    @ApiResponse(responseCode = "200", description = "후원 상세 조회 성공")
    @GetMapping("/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserPledgeDetailResponse> detail(@PathVariable Long pledgeId) {
        return ApiResult.success("후원 상세 조회에 성공했습니다.", service.detail(pledgeId));
    }

    /**
     * 후원 취소
     *
     * @param pledgeId 해당 후원 아이디
     * @return message
     */
    @Operation(summary = "후원 취소")
    @ApiResponse(responseCode = "200", description = "후원 취소 성공")
    @DeleteMapping("/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> cancel(@PathVariable Long pledgeId) {
        service.cancel(pledgeId);

        return ApiResult.success("후원 취소에 성공했습니다.");
    }
}
