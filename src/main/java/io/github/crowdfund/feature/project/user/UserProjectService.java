package io.github.crowdfund.feature.project.user;

import io.github.crowdfund.domain.project.ProjectStatus;
import io.github.crowdfund.domain.project.mapper.ProjectMapper;
import io.github.crowdfund.feature.project.user.dto.detail.ProjectDetail;
import io.github.crowdfund.feature.project.user.dto.detail.UserProjectDetailResponse;
import io.github.crowdfund.feature.project.user.dto.fetch.ProjectElement;
import io.github.crowdfund.feature.project.user.dto.fetch.UserProjectFetchResponse;
import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.github.crowdfund.global.common.dto.pagination.CursorResponse;
import io.github.crowdfund.global.common.pagination.CursorPaginationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProjectService {

    private final ProjectMapper projectMapper;

    /**
     * 프로젝트 목록 조회 도메인 로직
     */
    @Transactional
    public UserProjectFetchResponse fetch(List<ProjectStatus> statuses, Integer categoryId, CursorRequest cursorRequest, Integer limit) {
        // 1. 객체 내부 로직을 활용해 입력값 검증
        cursorRequest.validate();

        // 2. 데이터 목록 조회 (다음 페이지 존재 여부 확인을 위해 limit보다 1개를 더 조회)
        List<ProjectElement> projectList = projectMapper.findAll(
                statuses,
                categoryId,
                cursorRequest.createdAt(),
                cursorRequest.id(),
                limit + 1
        );

        // 3. 다음 요청에 사용할 복합 커서를 처리함
        CursorResponse<ProjectElement, CursorRequest> response = CursorPaginationProcessor.convertToCursorResponse(
                projectList,
                limit,
                item -> new CursorRequest(item.createdAt(), item.projectId())
        );

        return new UserProjectFetchResponse(response.content(), response.hasNext(), response.nextCursor());
    }

    /**
     * 프로젝트 상세 조회 도메인 로직
     */
    @Transactional
    public UserProjectDetailResponse detail(Long projectId) {
        ProjectDetail projectDetail = projectMapper.findByIdWithDetail(projectId);

        if (projectDetail == null) {
            throw new IllegalArgumentException("존재하지 않는 프로젝트입니다.");
        }

        return new UserProjectDetailResponse(projectDetail);
    }
}