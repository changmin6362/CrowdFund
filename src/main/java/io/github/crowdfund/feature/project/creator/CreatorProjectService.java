package io.github.crowdfund.feature.project.creator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.crowdfund.domain.pledge.PledgeRepository;
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
    private final PledgeRepository pledgeRepository;
    private final ObjectMapper objectMapper;

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
                toJsonString(request.contentBlocks()),
                request.goalAmount(),
                BigDecimal.ZERO,
                request.endAt(),
                ProjectStatus.ONGOING,
                LocalDateTime.now()
        );
        projectMapper.insert(creatorId, project);
        Long generatedId = projectMapper.getLastInsertId();
        return new CreatorProjectCreateResponse(generatedId);
    }

    /**
     * 프로젝트 제목과 본문 수정 도메인 로직
     */
    @Transactional
    public void update(SecurityUser securityUser, Long projectId, CreatorProjectUpdateRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));
        project.validateOwner(securityUser.getUserId());

        String newContentBlocks = toJsonString(request.contentBlocks());
        if (project.title().equals(request.title()) && project.contentBlocks().equals(newContentBlocks)) {
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }

        projectMapper.update(projectId, request.title(), newContentBlocks);
    }

    private String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("콘텐트 블록 데이터를 JSON으로 변환하는 중 에러가 발생했습니다.", e);
        }
    }

    /**
     * 프로젝트 삭제 도메인 로직
     */
    @Transactional
    public void delete(SecurityUser securityUser, Long projectId) {
        projectRepository.validateProjectOwner(projectId, securityUser.getUserId());
        projectMapper.deleteById(projectId);
    }

    /**
     * 내 프로젝트 조회 도메인 로직
     */
    @Transactional
    public CreatorProjectsFetchResponse fetch(Long userId) {
        List<ProjectInfo> projectList = projectMapper.findByCreatorId(userId);

        if (projectList.isEmpty()) {
            throw new IllegalArgumentException("등록된 프로젝트가 없습니다.");
        }

        return new CreatorProjectsFetchResponse(projectList);
    }

    /**
     * 후원자들의 배송지 목록 조회 도메인 로직
     */
    @Transactional
    public CreatorShippingInfosExtractResponse extract(SecurityUser securityUser, Long projectId) {
        projectRepository.validateProjectOwner(projectId, securityUser.getUserId());
        List<ShippingInfo> shippingInfos = projectMapper.findShippingInfosByProjectId(projectId);

        if (shippingInfos.isEmpty()) {
            throw new IllegalArgumentException("아직 후원자가 존재하지 않습니다.");
        }

        return new CreatorShippingInfosExtractResponse(shippingInfos);
    }

    /**
     * 프로젝트 취소 도메인 로직
     */
    @Transactional
    public void cancel(SecurityUser securityUser, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));
        project.validateOwner(securityUser.getUserId());

        if (!project.isOngoing()) {
            throw new IllegalArgumentException("진행 중인 프로젝트만 취소할 수 있습니다.");
        }

        if (pledgeRepository.existsByProjectId(projectId)) {
            throw new IllegalArgumentException("후원자가 있는 프로젝트는 취소할 수 없습니다.");
        }

        projectMapper.patchStatus(projectId, ProjectStatus.CANCELED);
    }
}