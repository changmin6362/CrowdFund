package io.github.crowdfund.feature.pledge.my;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.PledgeStatus;
import io.github.crowdfund.feature.pledge.my.dto.create.MyPledgeCreateRequest;
import io.github.crowdfund.feature.pledge.my.dto.create.MyPledgeCreateResponse;
import io.github.crowdfund.feature.pledge.my.dto.delete.MyPledgesDeleteResponse;
import io.github.crowdfund.feature.pledge.my.dto.detail.MyPledgeDetailResponse;
import io.github.crowdfund.feature.pledge.my.dto.fetch.MyPledgesFetchResponse;
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
@RequestMapping("/api/pledges/me")
@RequiredArgsConstructor
@Validated
@Tag(name = "07. Pledge - MY", description = "내 후원 API")
public class MyPledgeController {

    private final MyPledgeService service;

    /**
     * 프로젝트 후원하기
     *
     * @param request 후원 정보
     * @return message, pledgeId
     */
    @Operation(summary = "프로젝트 후원하기")
    @ApiResponse(responseCode = "201", description = "프로젝트 후원 성공 응답 예시")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<MyPledgeCreateResponse> create(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @RequestBody MyPledgeCreateRequest request) {
        return ApiResult.success("프로젝트 후원에 성공했습니다.", service.create(securityUser.getUserId(), request));
    }

    /**
     * 내 후원 상세 조회
     *
     * @param pledgeId 해당 펀딩 아이디
     * @return message, pledgeDetail
     */
    @Operation(summary = "후원 상세 조회")
    @ApiResponse(responseCode = "200", description = "후원 상세 조회 성공 응답 예시")
    @GetMapping("/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<MyPledgeDetailResponse> detail(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long pledgeId) {
        return ApiResult.success("내 후원 상세 조회에 성공했습니다.", service.detail(securityUser, pledgeId));
    }

    /**
     * 후원 취소
     *
     * @param pledgeId 해당 후원 아이디
     * @return message, deletedPledgeId
     */
    @Operation(summary = "후원 취소")
    @ApiResponse(responseCode = "200", description = "후원 취소 성공")
    @DeleteMapping("/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<MyPledgesDeleteResponse> cancel(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long pledgeId) {

        return ApiResult.success("후원 취소에 성공했습니다.", service.cancel(securityUser, pledgeId));
    }

    /**
     * 내가 후원한 프로젝트 목록 조회 (복합 커서 기반 최신순 페이지네이션)
     *
     * @param fulfillmentStatus 후원 이행 상태 필터 (null인 경우 모든 상태 조회)
     * @param pledgeStatus 후원 상태 필터 (null인 경우 모든 상태 조회)
     * @return message, pledges
     */
    @Operation(summary = "내가 후원한 프로젝트 목록 조회")
    @ApiResponse(responseCode = "200", description = "내가 후원한 프로젝트 목록 조회 성공 응답 예시")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<MyPledgesFetchResponse> fetch(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestParam(required = false) FulfillmentStatus fulfillmentStatus,
            @RequestParam(required = false) PledgeStatus pledgeStatus,
            @ParameterObject CursorRequest cursorRequest,
            @RequestParam(defaultValue = "10") @Positive Integer limit
    ) {
        return ApiResult.success("내가 후원한 프로젝트 목록 조회에 성공했습니다.", service.fetch(securityUser.getUserId(), fulfillmentStatus, pledgeStatus, cursorRequest, limit));
    }
}
