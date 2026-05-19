package io.github.authservice.crowdfund.feature.project;

import io.github.authservice.crowdfund.domain.project.*;
import io.github.authservice.crowdfund.domain.project.mapper.ProjectMapper;
import io.github.authservice.crowdfund.feature.project.command.CreateProjectCommand;
import io.github.authservice.crowdfund.feature.project.request.CreateProjectRequest;
import io.github.authservice.crowdfund.feature.project.request.PatchProjectStatusRequest;
import io.github.authservice.crowdfund.feature.project.request.PatchProjectRequest;
import io.github.authservice.crowdfund.feature.project.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectMapper projectMapper;

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
    public GetProjectResponse getProjects(List<ProjectStatus> statuses, Integer categoryId, LocalDateTime cursorCreatedAt, Long cursorId, Integer limit) {

        // 복합 커서 검증
        if ((cursorCreatedAt == null) != (cursorId == null)) {
            throw new IllegalArgumentException("cursorCreatedAt와 cursorId는 함께 전달되어야 합니다.");
        }

        // 다음 페이지 존재 여부 확인을 위해 limit보다 1개를 더 조회
        List<ProjectElement> projectList = projectMapper.findAll(statuses, categoryId, cursorCreatedAt, cursorId, limit + 1);

        boolean hasNext = false;
        NextCursor nextCursor = null;

        // 다음 페이지 존재 여부 확인 및 커서 생성 처리
        if (projectList.size() > limit) {
            hasNext = true;

            // 실제 표기할 limit 범위 내의 가장 마지막(limit - 1) 아이템 추출
            ProjectElement last = projectList.get(limit - 1);
            nextCursor = new NextCursor(last.createdAt(), last.projectId());

            // limit을 초과하여 조회된 N+1번째 가짜 데이터 제거
            projectList.remove((int) limit);
        }

        return new GetProjectResponse("프로젝트 목록 조회 성공", projectList, hasNext, nextCursor);
    }

    /**
     * 프로젝트 상세 조회 도메인 로직
     */
    public GetProjectDetailResponse getProjectDetail(Long projectId) {
        ProjectDetail projectDetail = projectMapper.findByIdWithDetail(projectId);
        
        if (projectDetail == null) {
            throw new IllegalArgumentException("존재하지 않는 프로젝트입니다.");
        }

        return new GetProjectDetailResponse("프로젝트 상세 정보 조회 성공", projectDetail);
    }

    /**
     * 프로젝트 제목과 본문 수정 도메인 로직
     */
    @Transactional
    public PatchProjectResponse patchProject(Long projectId, PatchProjectRequest request) {
        projectMapper.update(projectId, request.title(), request.contentBlocks());
        return new PatchProjectResponse("프로젝트 정보가 수정되었습니다.");
    }

    /**
     * 프로젝트 삭제 도메인 로직
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
        projectMapper.patchStatus(projectId, request.status());
        return new PatchProjectStatusResponse("프로젝트 상태가 성공적으로 변경되었습니다.");
    }
}