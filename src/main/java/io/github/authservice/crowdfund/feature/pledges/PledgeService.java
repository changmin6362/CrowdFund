package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.domain.payment.Payment;
import io.github.authservice.crowdfund.domain.payment.PaymentRepository;
import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.authservice.crowdfund.domain.pledge.Pledge;
import io.github.authservice.crowdfund.domain.pledge.PledgeRepository;
import io.github.authservice.crowdfund.domain.project.Project;
import io.github.authservice.crowdfund.domain.project.ProjectRepository;
import io.github.authservice.crowdfund.domain.reward.Reward;
import io.github.authservice.crowdfund.domain.reward.RewardRepository;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.domain.pledgeaddress.PledgeAddress;
import io.github.authservice.crowdfund.domain.pledgeaddress.PledgeAddressRepository;
import io.github.authservice.crowdfund.feature.pledges.request.CreatePledgeRequest;
import io.github.authservice.crowdfund.feature.pledges.request.PatchFulfillmentRequest;
import io.github.authservice.crowdfund.feature.pledges.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PledgeService {

    private final PledgeRepository pledgeRepository;
    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final PaymentRepository paymentRepository;
    private final PledgeAddressRepository pledgeAddressRepository;

    /**
     * 관리자용 후원 목록 조회 도메인 로직
     */
    @Transactional
    public GetAllPledgesResponse getAllPledges() {
        List<Pledge> pledges = pledgeRepository.findAll();
        List<PledgeSummary> summaries = pledges.stream()
                .map(this::mapToPledgeSummary)
                .collect(Collectors.toList());
        return new GetAllPledgesResponse("펀딩 리스트를 성공적으로 불러왔습니다.", summaries);
    }


    /**
     * 관리자용 후원 상세 조회 도메인 로직
     */
    @Transactional
    public GetAdminPledgeDetailResponse getAdminPledgeDetail(Long pledgeId) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 내역입니다."));

        User user = userRepository.findById(pledge.userId())
                .orElseThrow(() -> new IllegalStateException("해당 유저를 찾을 수 없습니다. ID: " + pledge.userId()));

        Project project = projectRepository.findById(pledge.projectId())
                .orElseThrow(() -> new IllegalStateException("해당 프로젝트를 찾을 수 없습니다. ID: " + pledge.projectId()));

        Optional<Payment> paymentOpt = paymentRepository.findByPledgeId(pledgeId);

        AdminUserDetail userDetail = new AdminUserDetail(
                user.id(),
                user.name(),
                user.nickname(),
                user.email(),
                user.phone()
        );

        AdminPaymentDetail paymentDetail = paymentOpt
                .map(p -> new AdminPaymentDetail(p.amount(), p.paymentMethod()))
                .orElse(new AdminPaymentDetail(pledge.amount(), "UNKNOWN"));

        AdminProjectDetail projectDetail = new AdminProjectDetail(
                project.id(),
                project.title()
        );

        AdminPledgeDetail pledgeDetail = new AdminPledgeDetail(
                pledge.id(),
                pledge.createdAt().toString(),
                pledge.fulfillmentStatus(),
                userDetail,
                paymentDetail,
                projectDetail
        );

        return new GetAdminPledgeDetailResponse("관리자용 후원 상세 정보를 성공적으로 불러왔습니다.", pledgeDetail);
    }

    /**
     * 후원 참여 도메인 로직
     */
    @Transactional
    public CreatePledgeResponse createPledge(Long userId, @Valid CreatePledgeRequest request) {
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

        return new CreatePledgeResponse("펀딩 후원이 성공하였습니다.", savedPledge.id());
    }

    /**
     * 후원 상세 조회 도메인 로직
     */
    @Transactional
    public GetPledgeDetailResponse getPledgeDetail(Long pledgeId) {
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

        return new GetPledgeDetailResponse("후원 상세 정보를 성공적으로 불러왔습니다.", pledgeDetail);
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
    public DeletePledgeResponse deletePledge(Long pledgeId) {
        if (!pledgeRepository.existsById(pledgeId)) {
            throw new IllegalArgumentException("존재하지 않는 후원 내역입니다.");
        }
        pledgeRepository.deleteById(pledgeId);
        return new DeletePledgeResponse("펀딩 주문을 성공적으로 취소했습니다.");
    }

    /**
     * 보상 이행 상태 갱신 도메인 로직
     */
    @Transactional
    public PatchFulfillmentResponse patchFulfillment(Long pledgeId, @Valid PatchFulfillmentRequest request) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 내역입니다."));

        LocalDateTime fulfilledAt = pledge.fulfilledAt();
        if (request.fulfillmentStatus() == FulfillmentStatus.COMPLETED) {
            fulfilledAt = LocalDateTime.now();
        }

        Pledge updatedPledge = new Pledge(
                pledge.id(),
                pledge.userId(),
                pledge.projectId(),
                pledge.rewardId(),
                pledge.amount(),
                request.fulfillmentStatus(),
                fulfilledAt,
                pledge.createdAt()
        );

        pledgeRepository.save(updatedPledge);

        return new PatchFulfillmentResponse("보상 이행 상태가 변경되었습니다.",
                new FulfillmentInfo(pledgeId, request.fulfillmentStatus(), fulfilledAt));
    }


    private PledgeSummary mapToPledgeSummary(Pledge pledge) {
        String userName = userRepository.findById(pledge.userId())
                .map(User::name)
                .orElseThrow(() -> new IllegalStateException("해당 유저를 찾을 수 없습니다. ID: " + pledge.userId()));

        String projectTitle = projectRepository.findById(pledge.projectId())
                .map(Project::title)
                .orElseThrow(() -> new IllegalStateException("해당 프로젝트를 찾을 수 없습니다. ID: " + pledge.projectId()));

        return new PledgeSummary(
                pledge.id(),
                pledge.userId(),
                userName,
                pledge.projectId(),
                projectTitle,
                pledge.rewardId(),
                pledge.amount(),
                pledge.fulfillmentStatus(),
                pledge.createdAt().toString()
        );
    }
}
