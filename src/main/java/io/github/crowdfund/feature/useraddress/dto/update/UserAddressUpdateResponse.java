package io.github.crowdfund.feature.useraddress.dto.update;

import io.github.crowdfund.feature.useraddress.dto.UserAddressInfo;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserAddressUpdateResponse(
        @Schema(description = "수정된 배송지 정보")
        UserAddressInfo updatedAddress
) {
}
