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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public AdminPledgesFetchResponse fetch() {
        List<Pledge> pledges = pledgeRepository.findAll();
        List<PledgeSummary> summaries = pledges.stream()
                .map(this::mapToPledgeSummary)
                .collect(Collectors.toList());
        return new AdminPledgesFetchResponse(summaries);
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
                pledge.fulfillmentStatus(),
                pledge.createdAt().toString()
        );
    }
}
