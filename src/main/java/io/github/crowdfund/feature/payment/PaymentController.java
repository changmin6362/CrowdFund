package io.github.crowdfund.feature.payment;

import io.github.crowdfund.feature.payment.dto.create.PaymentCreateRequest;
import io.github.crowdfund.feature.payment.dto.create.PaymentCreateResponse;
import io.github.crowdfund.feature.payment.dto.detail.PaymentDetailResponse;
import io.github.crowdfund.feature.payment.dto.history.PaymentHistoryResponse;
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
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Validated
@Tag(name = "Payment", description = "결제 API")
public class PaymentController {

    private final PaymentService service;

    /**
     * 결제 요청
     *
     * @param request 결제 요청 정보
     * @return message, paymentId
     */
    @Operation(summary = "결제 요청")
    @ApiResponse(responseCode = "201", description = "결제 요청 성공 응답 예시")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<PaymentCreateResponse> create(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @RequestBody PaymentCreateRequest request) {
        return ApiResult.success("결제 요청에 성공했습니다.", service.create(securityUser, request));
    }

    /**
     * 후원별 결제 상세 조회
     *
     * @param pledgeId 후원 ID
     * @return message, paymentDetail
     */
    @Operation(summary = "후원별 결제 상세 조회")
    @ApiResponse(responseCode = "200", description = "결제 상세 조회 성공 응답 예시")
    @GetMapping("/pledge/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<PaymentDetailResponse> detail(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long pledgeId) {
        return ApiResult.success("결제 상세 조회에 성공했습니다.", service.detail(securityUser, pledgeId));
    }

    /**
     * 결제 취소
     *
     * @param paymentId 결제 ID
     * @return message
     */
    @Operation(summary = "결제 취소")
    @ApiResponse(responseCode = "200", description = "결제 취소 성공 응답 예시")
    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> cancel(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long paymentId) {
        service.cancel(securityUser, paymentId);

        return ApiResult.success("결제 취소에 성공했습니다.");
    }

    /**
     * 결제 이력 조회
     *
     * @param paymentId 결제 ID
     * @return message, paymentHistories
     */
    @Operation(summary = "결제 이력 조회")
    @ApiResponse(responseCode = "200", description = "결제 이력 조회 성공 응답 예시")
    @GetMapping("/{paymentId}/history")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<PaymentHistoryResponse> history(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long paymentId) {
        return ApiResult.success("결제 이력 조회에 성공했습니다.", service.history(securityUser, paymentId));
    }
}
