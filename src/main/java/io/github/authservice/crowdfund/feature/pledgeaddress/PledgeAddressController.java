package io.github.authservice.crowdfund.feature.pledgeaddress;

import io.github.authservice.crowdfund.feature.pledgeaddress.request.ReplacePledgeAddressRequest;
import io.github.authservice.crowdfund.feature.pledgeaddress.response.GetPledgeAddressResponse;
import io.github.authservice.crowdfund.feature.pledgeaddress.response.ReplacePledgeAddressResponse;
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
    public GetPledgeAddressResponse getPledgeAddress(
            @PathVariable Long pledgesId
    ) {
        return service.getPledgeAddress(pledgesId);
    }

    /**
     * 후원 주소 교체
     *
     * @param pledgeId 후원 ID
     * @param request 교체할 주소 정보
     * @return message, replacedPledgeAddress
     */
    @PostMapping("/pledges/{pledgeId}/addresses")
    @ResponseStatus(HttpStatus.OK)
    public ReplacePledgeAddressResponse replacePledgeAddress(
            @PathVariable Long pledgeId,
            @RequestBody ReplacePledgeAddressRequest request
    ) {
        return service.replacePledgeAddress(pledgeId, request);
    }
}
