package io.github.authservice.crowdfund.feature.payment;

import io.github.authservice.crowdfund.feature.payment.request.CreatePaymentRequest;
import io.github.authservice.crowdfund.feature.payment.response.CancelPaymentResponse;
import io.github.authservice.crowdfund.feature.payment.response.CreatePaymentResponse;
import io.github.authservice.crowdfund.feature.payment.response.GetPaymentResponse;
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
    public CreatePaymentResponse createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        return service.createPayment(request);
    }

    /**
     * 후원별 결제 내역 조회
     *
     * @param pledgeId 후원 ID
     * @return message, paymentDetail
     */
    @GetMapping("/pledge/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public GetPaymentResponse getPaymentByPledgeId(@PathVariable Long pledgeId) {
        return service.getPaymentByPledgeId(pledgeId);
    }

    /**
     * 결제 취소
     *
     * @param paymentId 결제 ID
     * @return message
     */
    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public CancelPaymentResponse cancelPayment(@PathVariable Long paymentId) {
        return service.cancelPayment(paymentId);
    }
}
