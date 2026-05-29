package io.github.crowdfund.feature.pledges.creator;

import io.github.crowdfund.feature.pledges.creator.dto.fulfill.CreatorPledgeFulfillRequest;
import io.github.crowdfund.feature.pledges.creator.dto.fulfill.CreatorPledgeFulfillResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/creator/pledges")
@RequiredArgsConstructor
public class CreatorPledgeController {

    private final CreatorPledgeService service;

    /**
     * 보상 이행
     *
     * @param pledgeId 해당 후원 아이디
     * @param request  이행 상태 정보
     * @return message, updatedInfo
     */
    @Operation(summary = "보상 이행")
    @ApiResponse(responseCode = "200", description = "보상 이행 상태 갱신에 성공했습니다.")
    @PatchMapping("/{pledgeId}/fulfill")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<CreatorPledgeFulfillResponse> fulfill(
            @PathVariable Long pledgeId,
            @Valid @RequestBody CreatorPledgeFulfillRequest request) {
        return ApiResult.success("보상 이행 상태 갱신에 성공했습니다.", service.fulfill(pledgeId, request));
    }
}
