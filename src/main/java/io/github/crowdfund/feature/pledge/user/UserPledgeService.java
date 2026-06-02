package io.github.crowdfund.feature.pledge.user;

import io.github.crowdfund.domain.payment.Payment;
import io.github.crowdfund.domain.payment.PaymentMethod;
import io.github.crowdfund.domain.payment.PaymentRepository;
import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.Pledge;
import io.github.crowdfund.domain.pledge.PledgeRepository;
import io.github.crowdfund.domain.pledgeaddress.PledgeAddress;
import io.github.crowdfund.domain.pledgeaddress.PledgeAddressRepository;
import io.github.crowdfund.domain.project.Project;
import io.github.crowdfund.domain.project.ProjectRepository;
import io.github.crowdfund.domain.reward.Reward;
import io.github.crowdfund.domain.reward.RewardRepository;
import io.github.crowdfund.feature.pledge.user.dto.create.UserPledgeCreateRequest;
import io.github.crowdfund.feature.pledge.user.dto.create.UserPledgeCreateResponse;
import io.github.crowdfund.feature.pledge.user.dto.detail.UserPledgeDetail;
import io.github.crowdfund.feature.pledge.user.dto.detail.ShippingAddress;
import io.github.crowdfund.feature.pledge.user.dto.detail.UserPledgeDetailResponse;
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
        Reward reward = rewardRepository.findById(request.rewardId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리워드입니다."));

        if (!reward.projectId().equals(request.projectId())) {
            throw new IllegalArgumentException("해당 프로젝트의 리워드가 아닙니다.");
        }

        // TODO: 재고(stock) 체크 로직 추가 필요 여부 확인 (현재 Reward에 stock 필드 존재)
        // if (reward.stock() <= 0) { throw new RuntimeException("재고가 부족합니다."); }

        Pledge pledge = new Pledge(
                null,
                userId,
                request.projectId(),
                request.rewardId(),
                reward.price(),
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
        String paymentMethod = paymentOpt.map(p -> p.paymentMethod().name()).orElse(PaymentMethod.UNKNOWN.name());

        Optional<PledgeAddress> addressOpt = pledgeAddressRepository.findByPledgeId(pledge.id());
        ShippingAddress shippingAddress = addressOpt.map(addr -> new ShippingAddress(
                addr.recipientName(),
                addr.phone(),
                addr.addressMain(),
                addr.addressDetail(),
                addr.postalCode()
        )).orElse(null);

        UserPledgeDetail userPledgeDetail = new UserPledgeDetail(
                pledge.id(),
                pledge.createdAt().toString(),
                pledge.fulfillmentStatus(),
                project.title(),
                pledge.amount(),
                paymentMethod,
                reward.title(),
                shippingAddress
        );

        return new UserPledgeDetailResponse(userPledgeDetail);
    }

    /**
     * 후원 취소 도메인 로직
     */
    @Transactional
    public void cancel(Long pledgeId) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 내역입니다."));

        if (!pledge.canCancel()) {
            throw new IllegalStateException("이미 보상 이행이 시작되어 취소할 수 없습니다.");
        }

        pledgeRepository.deleteById(pledgeId);
    }
}
