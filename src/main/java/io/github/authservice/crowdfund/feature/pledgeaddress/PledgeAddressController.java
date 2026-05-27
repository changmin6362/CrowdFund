package io.github.authservice.crowdfund.feature.pledgeaddress;

import io.github.authservice.crowdfund.feature.pledgeaddress.dto.fetch.PledgeAddressFetchResponse;
import io.github.authservice.crowdfund.feature.pledgeaddress.dto.replace.PledgeAddressReplaceRequest;
import io.github.authservice.crowdfund.feature.pledgeaddress.dto.replace.PledgeAddressReplaceResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PledgeAddress", description = "후원 주소 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PledgeAddressController {

    private final PledgeAddressService service;

    /**
     * 후원 주소 조회
     *
     * @param pledgesId 후원 ID
     * @return message, pledgeAddress
     */
    @GetMapping("/pledges/{pledgesId}/addresses")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<PledgeAddressFetchResponse> fetch(
            @PathVariable Long pledgesId
    ) {
        return ApiResult.success("후원 주소 조회에 성공했습니다.", service.fetch(pledgesId));
    }

    /**
     * 후원 주소 교체
     *
     * @param pledgeId 후원 ID
     * @param request  교체할 주소 정보
     * @return message, replacedPledgeAddress
     */
    @PostMapping("/pledges/{pledgeId}/addresses")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<PledgeAddressReplaceResponse> replace(
            @PathVariable Long pledgeId,
            @RequestBody PledgeAddressReplaceRequest request
    ) {
        return ApiResult.success("후원 주소 교체에 성공했습니다.", service.replace(pledgeId, request));
    }
}
