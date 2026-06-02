package io.github.crowdfund.feature.pledge.my;

import io.github.crowdfund.domain.payment.Payment;
import io.github.crowdfund.domain.payment.PaymentMethod;
import io.github.crowdfund.domain.payment.PaymentRepository;
import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.Pledge;
import io.github.crowdfund.domain.pledge.PledgeRepository;
import io.github.crowdfund.domain.pledge.PledgeStatus;
import io.github.crowdfund.domain.pledgeaddress.PledgeAddress;
import io.github.crowdfund.domain.pledgeaddress.PledgeAddressRepository;
import io.github.crowdfund.domain.project.Project;
import io.github.crowdfund.domain.project.ProjectRepository;
import io.github.crowdfund.domain.reward.Reward;
import io.github.crowdfund.domain.reward.RewardRepository;
import io.github.crowdfund.domain.pledge.mapper.PledgeMapper;
import io.github.crowdfund.feature.pledge.my.dto.create.MyPledgeCreateRequest;
import io.github.crowdfund.feature.pledge.my.dto.create.MyPledgeCreateResponse;
import io.github.crowdfund.feature.pledge.my.dto.delete.MyPledgesDeleteResponse;
import io.github.crowdfund.feature.pledge.my.dto.detail.MyPledgeDetail;
import io.github.crowdfund.feature.pledge.my.dto.detail.ShippingAddress;
import io.github.crowdfund.feature.pledge.my.dto.detail.MyPledgeDetailResponse;
import io.github.crowdfund.feature.pledge.my.dto.fetch.MyPledgesFetchResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPledgeService {

    private final PledgeRepository pledgeRepository;
    private final RewardRepository rewardRepository;
    private final ProjectRepository projectRepository;
    private final PaymentRepository paymentRepository;
    private final PledgeAddressRepository pledgeAddressRepository;
    private final PledgeMapper pledgeMapper;

    /**
     * 프로젝트 후원하기 도메인 로직
     */
    @Transactional
    public MyPledgeCreateResponse create(Long userId, @Valid MyPledgeCreateRequest request) {
        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));

        if (!project.isOngoing()) {
            throw new IllegalStateException("현재 진행 중인 프로젝트가 아닙니다.");
        }

        if (pledgeRepository.existsByUserIdAndProjectId(userId, request.projectId())) {
            throw new IllegalStateException("이미 이 프로젝트에 후원하셨습니다.");
        }

        Reward reward = rewardRepository.findById(request.rewardId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리워드입니다."));

        if (!reward.projectId().equals(request.projectId())) {
            throw new IllegalArgumentException("해당 프로젝트의 리워드가 아닙니다.");
        }

        if (!reward.hasStock()) {
            throw new IllegalStateException("리워드 재고가 부족합니다.");
        }

        rewardRepository.save(reward.decreaseStock());

        Pledge pledge = new Pledge(
                null,
                userId,
                request.projectId(),
                request.rewardId(),
                reward.price(),
                PledgeStatus.PENDING,
                FulfillmentStatus.READY,
                null,
                LocalDateTime.now()
        );

        Pledge savedPledge = pledgeRepository.save(pledge);

        return new MyPledgeCreateResponse(savedPledge.id());
    }

    /**
     * 후원 상세 조회 도메인 로직
     */
    @Transactional
    public MyPledgeDetailResponse detail(Long pledgeId) {
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

        MyPledgeDetail myPledgeDetail = new MyPledgeDetail(
                pledge.id(),
                pledge.createdAt().toString(),
                pledge.status(),
                pledge.fulfillmentStatus(),
                project.title(),
                pledge.amount(),
                paymentMethod,
                reward.title(),
                shippingAddress
        );

        return new MyPledgeDetailResponse(myPledgeDetail);
    }

    /**
     * 후원 취소 도메인 로직
     */
    @Transactional
    public MyPledgesDeleteResponse cancel(Long pledgeId) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 내역입니다."));

        if (!pledge.canCancel()) {
            throw new IllegalStateException("이미 보상 이행이 시작되어 취소할 수 없습니다.");
        }

        Pledge canceledPledge = pledge.cancelPledge();
        pledgeRepository.save(canceledPledge);

        return new MyPledgesDeleteResponse(canceledPledge.id());
    }

    /**
     * 내가 후원한 프로젝트 목록 조회 도메인 로직
     */
    public MyPledgesFetchResponse fetch(Long userId, FulfillmentStatus status) {
        return new MyPledgesFetchResponse(pledgeMapper.findPledgesByUserId(userId, status));
    }
}
