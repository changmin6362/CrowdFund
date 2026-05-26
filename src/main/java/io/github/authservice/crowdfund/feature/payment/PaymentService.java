package io.github.authservice.crowdfund.feature.payment;

import io.github.authservice.crowdfund.domain.payment.Payment;
import io.github.authservice.crowdfund.domain.payment.PaymentRepository;
import io.github.authservice.crowdfund.domain.pledge.Pledge;
import io.github.authservice.crowdfund.domain.pledge.PledgeRepository;
import io.github.authservice.crowdfund.feature.payment.request.CreatePaymentRequest;
import io.github.authservice.crowdfund.feature.payment.response.CancelPaymentResponse;
import io.github.authservice.crowdfund.feature.payment.response.CreatePaymentResponse;
import io.github.authservice.crowdfund.feature.payment.response.GetPaymentResponse;
import io.github.authservice.crowdfund.feature.payment.response.PaymentDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PledgeRepository pledgeRepository;

    /**
     * 결제 요청 도메인 로직
     */
    @Transactional
    public CreatePaymentResponse createPayment(CreatePaymentRequest request) {
        Pledge pledge = pledgeRepository.findById(request.pledgeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 정보입니다."));

        if (!pledge.amount().equals(request.amount())) {
            throw new IllegalArgumentException("후원 금액과 결제 금액이 일치하지 않습니다.");
        }

        Payment payment = new Payment(
                null,
                request.pledgeId(),
                request.paymentMethod(),
                request.amount(),
                "PAID",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Payment saved = paymentRepository.save(payment);

        return new CreatePaymentResponse("결제가 완료되었습니다.", saved.id());
    }

    /**
     * 후원별 결제 내역 조회 도메인 로직
     */
    @Transactional(readOnly = true)
    public GetPaymentResponse getPaymentByPledgeId(Long pledgeId) {
        Payment payment = paymentRepository.findByPledgeId(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("결제 내역이 존재하지 않습니다."));

        PaymentDetail detail = new PaymentDetail(
                payment.id(),
                payment.pledgeId(),
                payment.paymentMethod(),
                payment.amount(),
                payment.status(),
                payment.paidAt(),
                payment.createdAt()
        );

        return new GetPaymentResponse("결제 내역 조회 성공", detail);
    }

    /**
     * 결제 취소 도메인 로직
     */
    @Transactional
    public CancelPaymentResponse cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제 정보입니다."));

        if ("CANCELED".equals(payment.status())) {
            throw new IllegalStateException("이미 취소된 결제입니다.");
        }

        Payment canceledPayment = new Payment(
                payment.id(),
                payment.pledgeId(),
                payment.paymentMethod(),
                payment.amount(),
                "CANCELED",
                payment.paidAt(),
                payment.createdAt()
        );

        paymentRepository.save(canceledPayment);

        return new CancelPaymentResponse("결제가 취소되었습니다.");
    }
}
