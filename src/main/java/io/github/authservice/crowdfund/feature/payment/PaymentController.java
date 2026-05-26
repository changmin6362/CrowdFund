package io.github.authservice.crowdfund.feature.payment;

import io.github.authservice.crowdfund.feature.payment.request.CreatePaymentRequest;
import io.github.authservice.crowdfund.feature.payment.response.CreatePaymentResponse;
import io.github.authservice.crowdfund.feature.payment.response.GetPaymentResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    /**
     * 결제 요청
     *
     * @param request 결제 요청 정보
     * @return message, paymentId
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CreatePaymentResponse> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        return ApiResult.success("결제 요청에 성공했습니다.", service.createPayment(request));
    }

    /**
     * 후원별 결제 내역 조회
     *
     * @param pledgeId 후원 ID
     * @return message, paymentDetail
     */
    @GetMapping("/pledge/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetPaymentResponse> getPaymentByPledgeId(@PathVariable Long pledgeId) {
        return ApiResult.success("결제 내역 조회에 성공했습니다.", service.getPaymentByPledgeId(pledgeId));
    }

    /**
     * 결제 취소
     *
     * @param paymentId 결제 ID
     * @return message
     */
    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> cancelPayment(@PathVariable Long paymentId) {
        service.cancelPayment(paymentId);

        return ApiResult.success("결제 취소에 성공했습니다.");
    }
}
