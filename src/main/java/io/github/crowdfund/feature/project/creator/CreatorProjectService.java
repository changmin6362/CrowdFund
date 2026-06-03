package io.github.crowdfund.feature.project.creator;

import io.github.crowdfund.domain.project.Project;
import io.github.crowdfund.domain.project.ProjectRepository;
import io.github.crowdfund.domain.project.ProjectStatus;
import io.github.crowdfund.domain.project.mapper.ProjectMapper;
import io.github.crowdfund.feature.project.creator.dto.create.CreatorProjectCreateRequest;
import io.github.crowdfund.feature.project.creator.dto.create.CreatorProjectCreateResponse;
import io.github.crowdfund.feature.project.creator.dto.extract.CreatorShippingInfosExtractResponse;
import io.github.crowdfund.feature.project.creator.dto.extract.ShippingInfo;
import io.github.crowdfund.feature.project.creator.dto.fetch.CreatorProjectsFetchResponse;
import io.github.crowdfund.feature.project.creator.dto.fetch.ProjectInfo;
import io.github.crowdfund.feature.project.creator.dto.update.CreatorProjectUpdateRequest;
import io.github.crowdfund.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreatorProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    /**
     * 프로젝트 생성 도메인 로직
     */
    @Transactional
    public CreatorProjectCreateResponse create(Long creatorId, CreatorProjectCreateRequest request) {
        Project project = new Project(
                null,
                request.categoryId(),
                creatorId,
                request.title(),
                request.contentBlocks(),
                request.goalAmount(),
                BigDecimal.ZERO,
                request.endAt(),
                ProjectStatus.ONGOING,
                LocalDateTime.now()
        );
        Long generatedId = projectMapper.insert(creatorId, project);
        return new CreatorProjectCreateResponse(generatedId);
    }

    /**
     * 프로젝트 제목과 본문 수정 도메인 로직
     */
    @Transactional
    public void update(SecurityUser securityUser, Long projectId, CreatorProjectUpdateRequest request) {
        validateProjectOwner(securityUser, projectId);
        projectMapper.update(projectId, request.title(), request.contentBlocks());
    }

    /**
     * 프로젝트 삭제 도메인 로직
     */
    @Transactional
    public void delete(SecurityUser securityUser, Long projectId) {
        validateProjectOwner(securityUser, projectId);
        projectMapper.deleteById(projectId);
    }

    /**
     * 내 프로젝트 조회 도메인 로직
     */
    @Transactional
    public CreatorProjectsFetchResponse fetch(Long userId) {
        List<ProjectInfo> projectList = projectMapper.findByCreatorId(userId);
        return new CreatorProjectsFetchResponse(projectList);
    }

    /**
     * 후원자들의 배송 정보 목록 조회 도메인 로직
     */
    @Transactional
    public CreatorShippingInfosExtractResponse extract(SecurityUser securityUser, Long projectId) {
        validateProjectOwner(securityUser, projectId);
        List<ShippingInfo> shippingInfos = projectMapper.findShippingInfosByProjectId(projectId);
        return new CreatorShippingInfosExtractResponse(shippingInfos);
    }

    private void validateProjectOwner(SecurityUser securityUser, Long projectId) {
        projectRepository.findById(projectId)
                .ifPresentOrElse(project -> {
                    if (!securityUser.isOwner(project.creatorId())) {
                        throw new IllegalArgumentException("본인의 프로젝트만 관리할 수 있습니다.");
                    }
                }, () -> {
                    throw new IllegalArgumentException("존재하지 않는 프로젝트입니다.");
                });
    }
}