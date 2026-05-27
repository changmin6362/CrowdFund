package io.github.crowdfund.feature.payment;

import io.github.crowdfund.feature.payment.dto.create.PaymentCreateRequest;
import io.github.crowdfund.feature.payment.dto.create.PaymentCreateResponse;
import io.github.crowdfund.feature.payment.dto.fetch.PaymentFetchResponse;
import io.github.crowdfund.global.common.ApiResult;
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
    public ApiResult<PaymentCreateResponse> create(@Valid @RequestBody PaymentCreateRequest request) {
        return ApiResult.success("결제 요청에 성공했습니다.", service.create(request));
    }

    /**
     * 후원별 결제 내역 조회
     *
     * @param pledgeId 후원 ID
     * @return message, paymentDetail
     */
    @GetMapping("/pledge/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<PaymentFetchResponse> fetch(@PathVariable Long pledgeId) {
        return ApiResult.success("결제 내역 조회에 성공했습니다.", service.fetch(pledgeId));
    }

    /**
     * 결제 취소
     *
     * @param paymentId 결제 ID
     * @return message
     */
    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> cancel(@PathVariable Long paymentId) {
        service.cancel(paymentId);

        return ApiResult.success("결제 취소에 성공했습니다.");
    }
}
