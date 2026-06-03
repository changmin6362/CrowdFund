package io.github.crowdfund.feature.pledgeaddress;

import io.github.crowdfund.feature.pledgeaddress.dto.fetch.PledgeAddressFetchResponse;
import io.github.crowdfund.feature.pledgeaddress.dto.replace.PledgeAddressReplaceRequest;
import io.github.crowdfund.feature.pledgeaddress.dto.replace.PledgeAddressReplaceResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pledges/{pledgeId}/address")
@RequiredArgsConstructor
@Validated
@Tag(name = "PledgeAddress", description = "후원별 배송 정보 API")
public class PledgeAddressController {

    private final PledgeAddressService service;

    /**
     * 참여한 후원의 배송 정보 조회
     *
     * @param pledgeId 후원 ID
     * @return message, pledgeAddress
     */
    @Operation(summary = "참여한 후원의 배송 정보 조회")
    @ApiResponse(responseCode = "200", description = "참여한 후원의 배송 정보 조회 성공 응답 예시")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<PledgeAddressFetchResponse> fetch(
            @PathVariable Long pledgeId
    ) {
        return ApiResult.success("참여한 후원의 배송 정보 조회에 성공했습니다.", service.fetch(pledgeId));
    }

    /**
     * 참여한 후원의 배송 정보 교체
     *
     * @param pledgeId 후원 ID
     * @param request  교체할 주소 정보
     * @return message, replacedPledgeAddress
     */
    @Operation(summary = "참여한 후원의 배송 정보 교체")
    @ApiResponse(responseCode = "200", description = "참여한 후원의 배송 정보 교체 성공 응답 예시")
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<PledgeAddressReplaceResponse> replace(
            @PathVariable Long pledgeId,
            @RequestBody PledgeAddressReplaceRequest request
    ) {
        return ApiResult.success("참여한 후원의 배송 정보 교체에 성공했습니다.", service.replace(pledgeId, request));
    }
}
