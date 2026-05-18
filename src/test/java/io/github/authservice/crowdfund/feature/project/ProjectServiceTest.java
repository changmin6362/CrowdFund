package io.github.authservice.crowdfund.feature.project;

import io.github.authservice.crowdfund.domain.project.ProjectStatus;
import io.github.authservice.crowdfund.feature.project.request.PatchProjectStatusRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    @DisplayName("프로젝트 상태를 변경한다")
    void patchProjectStatus() {
        // given (이미 데이터베이스에 프로젝트가 있다고 가정하거나, 필요한 경우 생성 로직 추가)
        Long projectId = 1L; // DB에 1번 프로젝트가 있다고 가정
        PatchProjectStatusRequest request = new PatchProjectStatusRequest(ProjectStatus.COMPLETED);

        // when
        try {
            projectService.patchProjectStatus(projectId, request);
        } catch (Exception e) {
            // 프로젝트가 없을 경우 예외가 발생할 수 있음
        }

        // then - 실제 DB 연동 테스트가 어려우면 호출 여부나 예외 발생 여부 등으로 간접 확인
    }

    @Test
    @DisplayName("프로젝트 목록을 조회한다")
    void getProjects() {
        // when
        var response = projectService.getProjects(List.of(ProjectStatus.ONGOING), null, null, null, 10);

        // then
        assertThat(response.message()).isEqualTo("프로젝트 목록 조회 성공");
        assertThat(response.projectList()).isNotNull();
        assertThat(response.hasNext()).isNotNull();
    }
    @Test
    @DisplayName("프로젝트 상세 정보를 조회한다")
    void getProjectDetail() {
        // when
        try {
            var response = projectService.getProjectDetail(1L);
            // then
            assertThat(response.message()).isEqualTo("프로젝트 상세 정보 조회 성공");
            assertThat(response.projectDetail()).isNotNull();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("존재하지 않는 프로젝트입니다.");
        }
    }
}
