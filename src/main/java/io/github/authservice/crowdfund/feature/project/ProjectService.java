package io.github.authservice.crowdfund.feature.project;

import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryRepository;
import io.github.authservice.crowdfund.domain.project.*;
import io.github.authservice.crowdfund.domain.reward.RewardRepository;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.project.command.CreateProjectCommand;
import io.github.authservice.crowdfund.feature.project.request.CreateProjectRequest;
import io.github.authservice.crowdfund.feature.project.request.PatchProjectStatusRequest;
import io.github.authservice.crowdfund.feature.project.request.UpdateProjectRequest;
import io.github.authservice.crowdfund.feature.project.response.*;
import io.github.authservice.crowdfund.feature.reward.response.RewardInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository repository;
    private final ProjectMapper projectMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RewardRepository rewardRepository;

    /**
     * 프로젝트 생성 도메인 로직
     */
    @Transactional
    public CreateProjectResponse createProject(Long creatorId, CreateProjectRequest request) {
        CreateProjectCommand command = new CreateProjectCommand(
                request.categoryId(),
                request.title(),
                request.content_blocks(),
                request.goalAmount(),
                BigDecimal.ZERO,
                request.endAt(),
                ProjectStatus.ONGOING
        );
        Long generatedId = projectMapper.insert(creatorId, command);
        return new CreateProjectResponse("프로젝트가 성공적으로 생성되었습니다.", generatedId);
    }

    /**
     * 프로젝트 목록 조회 도메인 로직
     */
    public GetProjectResponse getProjects(List<ProjectStatus> statuses, Integer categoryId) {
        List<ProjectInfo> projectList = projectMapper.findAll(statuses, categoryId);
        return new GetProjectResponse("프로젝트 목록 조회 성공", projectList);
    }

    /**
     * 프로젝트 상세 조회 도메인 로직
     */
    public GetProjectDetailResponse getProjectDetail(Long projectId) {
        Project project = repository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));

        String categoryName = categoryRepository.findById(project.categoryId())
                .map(Category::name)
                .orElse("미분류");

        String creatorNickname = userRepository.findById(project.creatorId())
                .map(User::nickname)
                .orElse("알 수 없음");

        List<RewardInfo> rewardList = rewardRepository.findByProjectId(projectId)
                .stream()
                .map(reward -> new RewardInfo(
                        reward.id(),
                        reward.title(),
                        reward.description(),
                        reward.amount().intValue(),
                        reward.stock()
                ))
                .collect(Collectors.toList());

        ProjectDetailInfo detailInfo = new ProjectDetailInfo(
                project.id(),
                categoryName,
                creatorNickname,
                project.title(),
                project.contentBlocks(),
                project.goalAmount(),
                project.currentAmount(),
                project.endAt(),
                project.status().name(),
                rewardList
        );

        return new GetProjectDetailResponse("프로젝트 상세 정보 조회 성공", detailInfo);
    }

    /**
     * 프로젝트 제목과 본문 수정 도메인 로직
     */
    @Transactional
    public UpdateProjectResponse updateProject(Long projectId, UpdateProjectRequest request) {
        projectMapper.update(projectId, request.title(), request.contentBlocks());
        return new UpdateProjectResponse("프로젝트 정보가 수정되었습니다.");
    }

    /**
     * 5. 사용자가 생성한 프로젝트를 삭제함.
     */
    @Transactional
    public DeleteProjectResponse deleteProject(Long projectId) {
        projectMapper.deleteById(projectId);
        return new DeleteProjectResponse("프로젝트 삭제 성공");
    }

    /**
     * 내 프로젝트 조회 도메인 로직
     */
    public GetMyProjectsResponse getMyProjects(Long userId) {
        List<ProjectInfo> projectList = projectMapper.findByCreatorId(userId);
        return new GetMyProjectsResponse("사용자별 프로젝트 조회 성공", projectList);
    }

    /**
     * 후원자들의 배송 정보 목록 조회 도메인 로직
     */
    public GetShippingInfosResponse getShippingInfos(Long projectId) {
        List<ShippingInfo> shippingInfos = projectMapper.findShippingInfosByProjectId(projectId);
        return new GetShippingInfosResponse("배송지 정보 조회 성공", shippingInfos);
    }

    /**
     * 프로젝트 상태 갱신 도메인 로직
     */
    @Transactional
    public PatchProjectStatusResponse patchProjectStatus(Long projectId, PatchProjectStatusRequest request) {
        projectMapper.updateStatus(projectId, request.status());
        return new PatchProjectStatusResponse("프로젝트 상태가 성공적으로 변경되었습니다.");
    }
}