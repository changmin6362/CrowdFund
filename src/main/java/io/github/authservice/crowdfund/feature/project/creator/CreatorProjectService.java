package io.github.authservice.crowdfund.feature.project.creator;

import io.github.authservice.crowdfund.domain.project.Project;
import io.github.authservice.crowdfund.domain.project.ProjectStatus;
import io.github.authservice.crowdfund.domain.project.mapper.ProjectMapper;
import io.github.authservice.crowdfund.feature.project.creator.dto.create.CreatorProjectCreateRequest;
import io.github.authservice.crowdfund.feature.project.creator.dto.create.CreatorProjectCreateResponse;
import io.github.authservice.crowdfund.feature.project.creator.dto.extract.CreatorShippingInfosExtractResponse;
import io.github.authservice.crowdfund.feature.project.creator.dto.extract.ShippingInfo;
import io.github.authservice.crowdfund.feature.project.creator.dto.fetch.CreatorProjectsFetchResponse;
import io.github.authservice.crowdfund.feature.project.creator.dto.fetch.ProjectInfo;
import io.github.authservice.crowdfund.feature.project.creator.dto.update.CreatorProjectUpdateRequest;
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
    public void update(Long projectId, CreatorProjectUpdateRequest request) {
        projectMapper.update(projectId, request.title(), request.contentBlocks());
    }

    /**
     * 프로젝트 삭제 도메인 로직
     */
    @Transactional
    public void delete(Long projectId) {
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
    public CreatorShippingInfosExtractResponse extract(Long projectId) {
        List<ShippingInfo> shippingInfos = projectMapper.findShippingInfosByProjectId(projectId);
        return new CreatorShippingInfosExtractResponse(shippingInfos);
    }
}