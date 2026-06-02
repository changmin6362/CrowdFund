package io.github.crowdfund.feature.pledge.admin;

import io.github.crowdfund.domain.payment.Payment;
import io.github.crowdfund.domain.payment.PaymentMethod;
import io.github.crowdfund.domain.payment.PaymentRepository;
import io.github.crowdfund.domain.pledge.Pledge;
import io.github.crowdfund.domain.pledge.PledgeRepository;
import io.github.crowdfund.domain.project.Project;
import io.github.crowdfund.domain.project.ProjectRepository;
import io.github.crowdfund.domain.user.User;
import io.github.crowdfund.domain.user.UserRepository;
import io.github.crowdfund.feature.pledge.admin.dto.detail.*;
import io.github.crowdfund.feature.pledge.admin.dto.fetch.PledgeSummary;
import io.github.crowdfund.feature.pledge.admin.dto.fetch.AdminPledgesFetchResponse;
import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.github.crowdfund.global.common.dto.pagination.CursorResponse;
import io.github.crowdfund.global.common.pagination.CursorPaginationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminPledgeService {

    private final PledgeRepository pledgeRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final PaymentRepository paymentRepository;

    /**
     * 관리자용 전체 후원 목록 조회 도메인 로직
     */
    public AdminPledgesFetchResponse fetch(CursorRequest cursorRequest, Integer limit) {
        cursorRequest.validate();

        // 관리자용은 데이터량이 많을 수 있으나 현재 Repository에 커서 기반 조회 메서드가 없으므로 
        // 전체 조회 후 메모리에서 처리하거나 Repository를 확장해야 함.
        // 여기서는 프로젝트 일관성을 위해 전체 조회 후 페이징 처리하는 방식으로 우선 구현함 (추후 Repository 확장 권장)
        List<Pledge> allPledges = pledgeRepository.findAll();
        
        // 최신순 정렬 (createdAt DESC, id DESC)
        List<Pledge> sortedPledges = allPledges.stream()
                .sorted(Comparator.comparing(Pledge::createdAt).reversed()
                        .thenComparing(Comparator.comparing(Pledge::id).reversed()))
                .collect(Collectors.toList());

        // 커서 적용
        List<Pledge> filteredPledges = sortedPledges;
        if (cursorRequest.createdAt() != null && cursorRequest.id() != null) {
            filteredPledges = sortedPledges.stream()
                    .filter(p -> p.createdAt().isBefore(cursorRequest.createdAt()) || 
                            (p.createdAt().isEqual(cursorRequest.createdAt()) && p.id() < cursorRequest.id()))
                    .collect(Collectors.toList());
        }

        // limit + 1개 추출
        List<PledgeSummary> summaries = filteredPledges.stream()
                .limit(limit + 1)
                .map(this::mapToPledgeSummary)
                .collect(Collectors.toList());

        CursorResponse<PledgeSummary, CursorRequest> response = CursorPaginationProcessor.convertToCursorResponse(
                summaries,
                limit,
                item -> {
                    // PledgeSummary의 createdAt은 String이므로 LocalDateTime으로 파싱 필요
                    LocalDateTime createdAt = LocalDateTime.parse(item.createdAt());
                    return new CursorRequest(createdAt, item.pledgeId());
                }
        );

        return new AdminPledgesFetchResponse(response.content(), response.hasNext(), response.nextCursor());
    }


    /**
     * 관리자용 후원 상세 조회 도메인 로직
     */
    @Transactional
    public AdminPledgeDetailResponse detail(Long pledgeId) {
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
                .map(p -> new AdminPaymentDetail(p.amount(), p.paymentMethod().name()))
                .orElse(new AdminPaymentDetail(pledge.amount(), PaymentMethod.UNKNOWN.name()));

        AdminProjectDetail projectDetail = new AdminProjectDetail(
                project.id(),
                project.title()
        );

        AdminPledgeDetail pledgeDetail = new AdminPledgeDetail(
                pledge.id(),
                pledge.createdAt().toString(),
                pledge.status(),
                pledge.fulfillmentStatus(),
                userDetail,
                paymentDetail,
                projectDetail
        );

        return new AdminPledgeDetailResponse(pledgeDetail);
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
                pledge.status(),
                pledge.fulfillmentStatus(),
                pledge.createdAt().toString()
        );
    }
}
