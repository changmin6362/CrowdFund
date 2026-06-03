package io.github.crowdfund.feature.payment;

import io.github.crowdfund.domain.payment.Payment;
import io.github.crowdfund.domain.payment.PaymentRepository;
import io.github.crowdfund.domain.payment.PaymentStatus;
import io.github.crowdfund.domain.paymenthistory.PaymentHistory;
import io.github.crowdfund.domain.paymenthistory.PaymentHistoryRepository;
import io.github.crowdfund.domain.pledge.Pledge;
import io.github.crowdfund.domain.pledge.PledgeRepository;
import io.github.crowdfund.feature.payment.dto.create.PaymentCreateRequest;
import io.github.crowdfund.feature.payment.dto.create.PaymentCreateResponse;
import io.github.crowdfund.feature.payment.dto.detail.PaymentDetailResponse;
import io.github.crowdfund.feature.payment.dto.detail.PaymentDetail;
import io.github.crowdfund.feature.payment.dto.history.PaymentHistoryInfo;
import io.github.crowdfund.feature.payment.dto.history.PaymentHistoryResponse;
import io.github.crowdfund.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PledgeRepository pledgeRepository;

    /**
     * 결제 요청 도메인 로직
     */
    @Transactional
    public PaymentCreateResponse create(SecurityUser securityUser, PaymentCreateRequest request) {
        if (paymentRepository.findByPledgeId(request.pledgeId()).isPresent()) {
            throw new IllegalStateException("이미 결제가 완료되었습니다.");
        }

        Pledge pledge = pledgeRepository.findById(request.pledgeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 정보입니다."));

        if (!securityUser.isOwner(pledge.userId())) {
            throw new IllegalArgumentException("본인의 후원 내역만 결제할 수 있습니다.");
        }

        if (pledge.amount().compareTo(request.amount()) != 0) {
            throw new IllegalArgumentException("후원 금액과 결제 금액이 일치하지 않습니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        Payment payment = new Payment(
                null,
                request.pledgeId(),
                request.paymentMethod(),
                request.amount(),
                PaymentStatus.PAID,
                now,
                now,
                now
        );

        Payment saved = paymentRepository.save(payment);

        pledgeRepository.save(pledge.completePayment());

        PaymentHistory history = new PaymentHistory(
                null,
                saved.id(),
                PaymentStatus.PAID,
                now,
                "최초 결제 완료",
                null
        );
        paymentHistoryRepository.save(history);

        return new PaymentCreateResponse(saved.id());
    }

    /**
     * 후원별 결제 내역 조회 도메인 로직
     */
    @Transactional(readOnly = true)
    public PaymentDetailResponse detail(SecurityUser securityUser, Long pledgeId) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 정보입니다."));

        if (!securityUser.isOwner(pledge.userId())) {
            throw new IllegalArgumentException("본인의 결제 내역만 조회할 수 있습니다.");
        }

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

        return new PaymentDetailResponse(detail);
    }

    /**
     * 결제 취소 도메인 로직
     */
    @Transactional
    public void cancel(SecurityUser securityUser, Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제 정보입니다."));

        Pledge pledge = pledgeRepository.findById(payment.pledgeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 정보입니다."));

        if (!securityUser.isOwner(pledge.userId())) {
            throw new IllegalArgumentException("본인의 결제만 취소할 수 있습니다.");
        }

        if (payment.status() == PaymentStatus.CANCELED) {
            throw new IllegalStateException("이미 취소된 결제입니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        Payment canceledPayment = new Payment(
                payment.id(),
                payment.pledgeId(),
                payment.paymentMethod(),
                payment.amount(),
                PaymentStatus.CANCELED,
                payment.paidAt(),
                payment.createdAt(),
                now
        );

        paymentRepository.save(canceledPayment);

        PaymentHistory history = new PaymentHistory(
                null,
                payment.id(),
                PaymentStatus.CANCELED,
                now,
                "사용자 요청에 의한 결제 취소",
                null
        );
        paymentHistoryRepository.save(history);
    }

    /**
     * 결제 이력 조회 도메인 로직
     */
    @Transactional(readOnly = true)
    public PaymentHistoryResponse history(SecurityUser securityUser, Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제 정보입니다."));

        Pledge pledge = pledgeRepository.findById(payment.pledgeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 정보입니다."));

        if (!securityUser.isOwner(pledge.userId())) {
            throw new IllegalArgumentException("본인의 결제 이력만 조회할 수 있습니다.");
        }

        List<PaymentHistoryInfo> items = paymentHistoryRepository.findByPaymentIdOrderByChangedAtDesc(paymentId)
                .stream()
                .map(it -> new PaymentHistoryInfo(
                        it.id(),
                        it.status(),
                        it.changedAt(),
                        it.reason(),
                        it.pgTransactionId()
                ))
                .toList();

        return new PaymentHistoryResponse(items);
    }
}
