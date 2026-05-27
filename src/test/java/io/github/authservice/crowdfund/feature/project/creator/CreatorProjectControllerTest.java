package io.github.authservice.crowdfund.feature.project.creator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryRepository;
import io.github.authservice.crowdfund.domain.project.Project;
import io.github.authservice.crowdfund.domain.project.ProjectRepository;
import io.github.authservice.crowdfund.domain.project.ProjectStatus;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.project.creator.dto.create.CreatorProjectCreateResponse;
import io.github.authservice.crowdfund.feature.project.creator.dto.fetch.CreatorProjectsFetchResponse;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CreatorProjectControllerTest {

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
                null, "creator_" + System.currentTimeMillis() + "@test.com", "pass", "창작자", "홍길동", "010-1111-2222", "USER", LocalDateTime.now(), LocalDateTime.now(), null
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
                    "contentBlocks": "[{\\"type\\":\\"text\\",\\"content\\":\\"내용\\"}]",
                    "goalAmount": 1000000,
                    "endAt": "%s"
                }
                """.formatted(savedCategory.id(), LocalDateTime.now().plusDays(30));

        MvcResult result = mockMvc.perform(post("/api/projects/{creatorId}", savedUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        ApiResult<CreatorProjectCreateResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("프로젝트 생성에 성공했습니다.");
        assertThat(apiResult.data().projectId()).isNotNull();
    }

    @Test
    void 프로젝트_수정_테스트() throws Exception {
        Project savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "원래 제목", "[{\"type\":\"text\",\"content\":\"원래 내용\"}]", new BigDecimal("50000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(10), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        String patchRequest = """
                {
                    "title": "수정된 제목",
                    "contentBlocks": "[{\\"type\\":\\"text\\",\\"content\\":\\"수정된 내용\\"}]"
                }
                """;

        MvcResult result = mockMvc.perform(patch("/api/projects/{projectId}", savedProject.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<Void> apiResult = TestUtils.convertToApiResult(result, objectMapper, null);

        assertThat(apiResult.message()).isEqualTo("프로젝트 제목과 본문 수정에 성공했습니다.");
    }

    @Test
    void 프로젝트_삭제_테스트() throws Exception {
        Project savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "삭제할 프로젝트", "[{\"type\":\"text\",\"content\":\"내용\"}]", new BigDecimal("50000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(10), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(delete("/api/projects/{projectId}", savedProject.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<Void> apiResult = TestUtils.convertToApiResult(result, objectMapper, null);

        assertThat(apiResult.message()).isEqualTo("프로젝트 삭제에 성공했습니다.");
    }

    @Test
    void 내_프로젝트_목록_조회_테스트() throws Exception {
        projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "내 프로젝트", "[{\"type\":\"text\",\"content\":\"내용\"}]", new BigDecimal("50000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(10), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(get("/api/users/me/projects/{userId}", savedUser.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<CreatorProjectsFetchResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("내 프로젝트 조회에 성공했습니다.");
        assertThat(apiResult.data().projects()).isNotEmpty();
    }
}
