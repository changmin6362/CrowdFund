package io.github.crowdfund.feature.project.creator.dto.extract;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CreatorShippingInfosExtractResponse(
        @Schema(description = "배송 정보 목록")
        List<ShippingInfo> shippingInfos
) {
}