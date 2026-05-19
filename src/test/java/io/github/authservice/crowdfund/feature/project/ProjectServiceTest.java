package io.github.authservice.crowdfund.feature.project;

import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryRepository;
import io.github.authservice.crowdfund.domain.project.Project;
import io.github.authservice.crowdfund.domain.project.ProjectRepository;
import io.github.authservice.crowdfund.domain.project.ProjectStatus;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProjectServiceTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    private User savedUser;
    private Category savedCategory;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        savedUser = userRepository.save(new User(
                null, "creator@test.com", "pass", "creator", "creator", "010-1111-2222", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        savedCategory = categoryRepository.save(new Category(
                null, null, "테스트 카테고리", 1, 10, true
        ));
    }

    @Test
    void 프로젝트_생성_테스트() throws Exception {
        String createRequest = """
                {
                    "categoryId": %d,
                    "title": "새로운 프로젝트",
                    "contentBlocks": "프로젝트 상세 내용",
                    "goalAmount": 1000000,
                    "endAt": "%s"
                }
                """.formatted(savedCategory.id(), LocalDateTime.now().plusDays(30));

        mockMvc.perform(post("/api/projects/{creatorId}", savedUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("프로젝트 생성이 완료되었습니다."))
                .andExpect(jsonPath("$.projectId").exists())
                .andDo(print());
    }

    @Test
    void 프로젝트_목록_조회_테스트() throws Exception {
        projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "프로젝트 1", "내용 1", new BigDecimal("10000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(10), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        mockMvc.perform(get("/api/projects")
                        .param("statuses", "ONGOING")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("프로젝트 목록 조회 성공"))
                .andExpect(jsonPath("$.projectList").isArray())
                .andDo(print());
    }

    @Test
    void 프로젝트_상세_조회_테스트() throws Exception {
        Project savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "상세 조회 프로젝트", "상세 내용", new BigDecimal("50000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(10), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        mockMvc.perform(get("/api/projects/{projectId}", savedProject.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("프로젝트 상세 정보 조회 성공"))
                .andExpect(jsonPath("$.projectDetail.title").value("상세 조회 프로젝트"))
                .andDo(print());
    }

    @Test
    void 프로젝트_수정_테스트() throws Exception {
        Project savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "원래 제목", "원래 내용", new BigDecimal("50000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(10), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        String patchRequest = """
                {
                    "title": "수정된 제목",
                    "contentBlocks": "수정된 내용"
                }
                """;

        mockMvc.perform(patch("/api/projects/{projectId}", savedProject.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("프로젝트 정보 수정 성공"))
                .andDo(print());
    }

    @Test
    void 프로젝트_상태_변경_테스트() throws Exception {
        Project savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "상태 변경 프로젝트", "내용", new BigDecimal("50000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(10), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        String statusRequest = """
                {
                    "status": "COMPLETED"
                }
                """;

        mockMvc.perform(patch("/api/projects/{projectId}/status", savedProject.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(statusRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("프로젝트 상태 변경 성공"))
                .andDo(print());
    }

    @Test
    void 프로젝트_삭제_테스트() throws Exception {
        Project savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "삭제할 프로젝트", "내용", new BigDecimal("50000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(10), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        mockMvc.perform(delete("/api/projects/{projectId}", savedProject.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("프로젝트가 삭제되었습니다."))
                .andDo(print());
    }

    @Test
    void 내_프로젝트_목록_조회_테스트() throws Exception {
        projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "내 프로젝트", "내용", new BigDecimal("50000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(10), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        mockMvc.perform(get("/api/users/me/projects/{userId}", savedUser.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("내 프로젝트 목록 조회 성공"))
                .andDo(print());
    }

    @Test
    void 프로젝트_조회_실패_테스트() throws Exception {
        mockMvc.perform(get("/api/projects/9999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("존재하지 않는 프로젝트입니다."))
                .andDo(print());
    }
}
