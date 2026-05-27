package io.github.authservice.crowdfund.feature.project.admin;

import io.github.authservice.crowdfund.domain.project.mapper.ProjectMapper;
import io.github.authservice.crowdfund.feature.project.admin.dto.update.AdminProjectUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminProjectService {

    private final ProjectMapper projectMapper;

    /**
     * 프로젝트 상태 갱신 도메인 로직
     */
    @Transactional
    public void update(Long projectId, AdminProjectUpdateRequest request) {
        projectMapper.patchStatus(projectId, request.status());
    }
}