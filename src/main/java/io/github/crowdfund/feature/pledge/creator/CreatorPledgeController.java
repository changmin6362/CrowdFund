package io.github.crowdfund.feature.pledge.creator;

import io.github.crowdfund.feature.pledge.creator.dto.fulfill.CreatorPledgeFulfillRequest;
import io.github.crowdfund.feature.pledge.creator.dto.fulfill.CreatorPledgeFulfillResponse;
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
@RequestMapping("/api/creator/pledges")
@RequiredArgsConstructor
@Validated
@Tag(name = "Pledge - Creator", description = "창작자용 후원 API")
public class CreatorPledgeController {

    private final CreatorPledgeService service;

    /**
     * 보상 이행 상태 변경
     *
     * @param pledgeId 해당 후원 아이디
     * @param request  이행 상태 정보
     * @return message, updatedInfo
     */
    @Operation(summary = "보상 이행 상태 변경")
    @ApiResponse(responseCode = "200", description = "보상 이행 상태 변경 성공 응답 예시")
    @PatchMapping("/{pledgeId}/fulfill")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<CreatorPledgeFulfillResponse> fulfill(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long pledgeId,
            @Valid @RequestBody CreatorPledgeFulfillRequest request) {
        return ApiResult.success("보상 이행 상태 변경에 성공했습니다.", service.fulfill(securityUser, pledgeId, request));
    }
}
