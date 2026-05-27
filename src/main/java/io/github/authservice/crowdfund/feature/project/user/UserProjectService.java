package io.github.authservice.crowdfund.feature.project.user;

import io.github.authservice.crowdfund.domain.project.ProjectStatus;
import io.github.authservice.crowdfund.domain.project.mapper.ProjectMapper;
import io.github.authservice.crowdfund.feature.project.user.dto.detail.ProjectDetail;
import io.github.authservice.crowdfund.feature.project.user.dto.detail.UserProjectDetailResponse;
import io.github.authservice.crowdfund.feature.project.user.dto.fetch.NextCursor;
import io.github.authservice.crowdfund.feature.project.user.dto.fetch.ProjectElement;
import io.github.authservice.crowdfund.feature.project.user.dto.fetch.UserProjectFetchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public UserProjectFetchResponse fetch(List<ProjectStatus> statuses, Integer categoryId, LocalDateTime cursorCreatedAt, Long cursorId, Integer limit) {

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

        return new UserProjectFetchResponse(projectList, hasNext, nextCursor);
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