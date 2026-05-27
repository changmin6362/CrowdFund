package io.github.authservice.crowdfund.feature.pledges.user;

import io.github.authservice.crowdfund.domain.payment.Payment;
import io.github.authservice.crowdfund.domain.payment.PaymentRepository;
import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.authservice.crowdfund.domain.pledge.Pledge;
import io.github.authservice.crowdfund.domain.pledge.PledgeRepository;
import io.github.authservice.crowdfund.domain.pledgeaddress.PledgeAddress;
import io.github.authservice.crowdfund.domain.pledgeaddress.PledgeAddressRepository;
import io.github.authservice.crowdfund.domain.project.Project;
import io.github.authservice.crowdfund.domain.project.ProjectRepository;
import io.github.authservice.crowdfund.domain.reward.Reward;
import io.github.authservice.crowdfund.domain.reward.RewardRepository;
import io.github.authservice.crowdfund.feature.pledges.user.dto.create.UserPledgeCreateRequest;
import io.github.authservice.crowdfund.feature.pledges.user.dto.create.UserPledgeCreateResponse;
import io.github.authservice.crowdfund.feature.pledges.user.dto.detail.PledgeDetail;
import io.github.authservice.crowdfund.feature.pledges.user.dto.detail.ShippingAddress;
import io.github.authservice.crowdfund.feature.pledges.user.dto.detail.UserPledgeDetailResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserPledgeService {

    private final PledgeRepository pledgeRepository;
    private final RewardRepository rewardRepository;
    private final ProjectRepository projectRepository;
    private final PaymentRepository paymentRepository;
    private final PledgeAddressRepository pledgeAddressRepository;

    /**
     * 후원 참여 도메인 로직
     */
    @Transactional
    public UserPledgeCreateResponse create(Long userId, @Valid UserPledgeCreateRequest request) {
        Reward reward = rewardRepository.findById(request.reward_id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리워드입니다."));

        if (!reward.projectId().equals(request.project_id())) {
            throw new IllegalArgumentException("해당 프로젝트의 리워드가 아닙니다.");
        }

        // TODO: 재고(stock) 체크 로직 추가 필요 여부 확인 (현재 Reward에 stock 필드 존재)
        // if (reward.stock() <= 0) { throw new RuntimeException("재고가 부족합니다."); }

        Pledge pledge = new Pledge(
                null,
                userId,
                request.project_id(),
                request.reward_id(),
                reward.price().longValue(),
                FulfillmentStatus.READY,
                null,
                LocalDateTime.now()
        );

        Pledge savedPledge = pledgeRepository.save(pledge);

        return new UserPledgeCreateResponse(savedPledge.id());
    }

    /**
     * 후원 상세 조회 도메인 로직
     */
    @Transactional
    public UserPledgeDetailResponse detail(Long pledgeId) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 내역입니다."));

        Project project = projectRepository.findById(pledge.projectId())
                .orElseThrow(() -> new IllegalStateException("해당 프로젝트를 찾을 수 없습니다. ID: " + pledge.projectId()));

        Reward reward = rewardRepository.findById(pledge.rewardId())
                .orElseThrow(() -> new IllegalStateException("해당 리워드를 찾을 수 없습니다. ID: " + pledge.rewardId()));

        Optional<Payment> paymentOpt = paymentRepository.findByPledgeId(pledge.id());
        String paymentMethod = paymentOpt.map(p -> translatePaymentMethod(p.paymentMethod())).orElse("미지정");

        Optional<PledgeAddress> addressOpt = pledgeAddressRepository.findByPledgeId(pledge.id());
        ShippingAddress shippingAddress = addressOpt.map(addr -> new ShippingAddress(
                addr.recipientName(),
                addr.phone(),
                addr.addressMain(),
                addr.addressDetail(),
                // 배송 시작 시(또는 완료 시) 노출될 수 있는 정보로 가정 (여기서는 status가 SHIPPED/COMPLETED일 때만 노출하는 예시 로직)
                (pledge.fulfillmentStatus() == FulfillmentStatus.READY) ? "배송 준비중" : addr.postalCode()
        )).orElse(null);

        PledgeDetail pledgeDetail = new PledgeDetail(
                pledge.id(),
                pledge.createdAt().toString(),
                pledge.fulfillmentStatus(),
                project.title(),
                pledge.amount(),
                paymentMethod,
                reward.title(),
                shippingAddress
        );

        return new UserPledgeDetailResponse(pledgeDetail);
    }

    private String translatePaymentMethod(String method) {
        if (method == null) return "미지정";
        return switch (method.toUpperCase()) {
            case "CREDIT_CARD" -> "신용카드";
            case "TRANSFER" -> "계좌이체";
            case "KAKAOPAY" -> "카카오페이";
            default -> method;
        };
    }

    /**
     * 후원 취소 도메인 로직
     */
    @Transactional
    public void cancel(Long pledgeId) {
        if (!pledgeRepository.existsById(pledgeId)) {
            throw new IllegalArgumentException("존재하지 않는 후원 내역입니다.");
        }
        pledgeRepository.deleteById(pledgeId);
    }
}
