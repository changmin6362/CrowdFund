package io.github.crowdfund.feature.project.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.crowdfund.domain.category.Category;
import io.github.crowdfund.domain.category.CategoryRepository;
import io.github.crowdfund.domain.project.Project;
import io.github.crowdfund.domain.project.ProjectRepository;
import io.github.crowdfund.domain.project.ProjectStatus;
import io.github.crowdfund.domain.user.User;
import io.github.crowdfund.domain.user.UserRepository;
import io.github.crowdfund.feature.project.user.dto.detail.UserProjectDetailResponse;
import io.github.crowdfund.feature.project.user.dto.fetch.UserProjectFetchResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.github.crowdfund.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserProjectControllerTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User savedUser;
    private Category savedCategory;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        savedUser = userRepository.save(new User(
                null, "user_" + System.currentTimeMillis() + "@test.com", "pass", "후원자", "김후원", "010-1111-2222", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        savedCategory = categoryRepository.save(new Category(
                null, null, "테스트 카테고리", 1, 10, true
        ));
    }

    @Test
    void 프로젝트_목록_조회_테스트() throws Exception {
        projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "프로젝트 1", "[{\"type\":\"text\",\"content\":\"내용 1\"}]", new BigDecimal("10000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(10), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(get("/api/projects")
                        .param("statuses", "ONGOING")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<UserProjectFetchResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("프로젝트 목록 조회에 성공했습니다.");
        assertThat(apiResult.data().projectList()).isNotEmpty();
    }

    @Test
    void 프로젝트_상세_조회_테스트() throws Exception {
        Project savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "상세 조회 프로젝트", "[{\"type\":\"text\",\"content\":\"상세 내용\"}]", new BigDecimal("50000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(10), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(get("/api/projects/{projectId}", savedProject.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<UserProjectDetailResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("프로젝트 상세 조회에 성공했습니다.");
        assertThat(apiResult.data().projectDetail().title()).isEqualTo("상세 조회 프로젝트");
    }

    @Test
    void 프로젝트_조회_실패_테스트() throws Exception {
        mockMvc.perform(get("/api/projects/9999"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
