package io.github.crowdfund.feature.pledge.admin;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.PledgeStatus;
import io.github.crowdfund.feature.pledge.admin.dto.detail.AdminPledgeDetailResponse;
import io.github.crowdfund.feature.pledge.admin.dto.fetch.AdminPledgesFetchResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/pledges")
@RequiredArgsConstructor
@Tag(name = "Pledge - Admin", description = "관리자용 후원 API")
public class AdminPledgeController {

    private final AdminPledgeService service;

    /**
     * 관리자용 전체 후원 목록 조회 (복합 커서 기반 최신순 페이지네이션)
     *
     * @param fulfillmentStatus 후원 이행 상태 필터 (null인 경우 모든 상태 조회)
     * @param pledgeStatus 후원 상태 필터 (null인 경우 모든 상태 조회)
     * @return message, pledges, hasNext, nextCursor
     */
    @Operation(summary = "관리자용 전체 후원 목록 조회")
    @ApiResponse(responseCode = "200", description = "관리자용 전체 후원 목록 조회 성공 응답 예시")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<AdminPledgesFetchResponse> fetch(
            @RequestParam(required = false) FulfillmentStatus fulfillmentStatus,
            @RequestParam(required = false) PledgeStatus pledgeStatus,
            @ParameterObject CursorRequest cursorRequest,
            @RequestParam(defaultValue = "10") @Positive Integer limit
    ) {
        return ApiResult.success("전체 후원 목록 조회에 성공했습니다.", service.fetch(fulfillmentStatus, pledgeStatus, cursorRequest, limit));
    }

    /**
     * 관리자용 후원 상세 조회
     *
     * @param pledgeId 후원 ID
     * @return message, adminPledgeDetail
     */
    @Operation(summary = "관리자용 후원 상세 조회")
    @ApiResponse(responseCode = "200", description = "관리자용 후원 상세 조회 성공 응답 예시")
    @GetMapping("/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<AdminPledgeDetailResponse> detail(@PathVariable Long pledgeId) {
        return ApiResult.success("관리자용 후원 상세 조회에 성공했습니다.", service.detail(pledgeId));
    }
}
