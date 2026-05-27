package io.github.authservice.crowdfund.feature.project.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryRepository;
import io.github.authservice.crowdfund.domain.project.Project;
import io.github.authservice.crowdfund.domain.project.ProjectRepository;
import io.github.authservice.crowdfund.domain.project.ProjectStatus;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.global.common.ApiResult;
import io.github.authservice.crowdfund.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminProjectControllerTest {

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
                null, "admin_" + System.currentTimeMillis() + "@test.com", "pass", "admin", "관리자", "010-1111-2222", "ADMIN", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        savedCategory = categoryRepository.save(new Category(
                null, null, "테스트 카테고리", 1, 10, true
        ));
    }

    @Test
    void 프로젝트_상태_변경_테스트() throws Exception {
        Project savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "상태 변경 프로젝트", "[{\"type\":\"text\",\"content\":\"내용\"}]", new BigDecimal("50000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(10), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        String statusRequest = """
                {
                    "status": "COMPLETED"
                }
                """;

        MvcResult result = mockMvc.perform(patch("/api/admin/projects/{projectId}/status", savedProject.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(statusRequest))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<Void> apiResult = TestUtils.convertToApiResult(result, objectMapper, null);

        assertThat(apiResult.message()).isEqualTo("프로젝트 상태 변경에 성공했습니다.");
    }
}
