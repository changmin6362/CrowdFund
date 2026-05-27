package io.github.authservice.crowdfund.feature.reward.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryRepository;
import io.github.authservice.crowdfund.domain.project.Project;
import io.github.authservice.crowdfund.domain.project.ProjectRepository;
import io.github.authservice.crowdfund.domain.project.ProjectStatus;
import io.github.authservice.crowdfund.domain.reward.Reward;
import io.github.authservice.crowdfund.domain.reward.RewardRepository;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.reward.user.dto.fetch.UserRewardsFetchResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
import io.github.authservice.crowdfund.utils.TestUtils;
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
class UserRewardControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Project savedProject;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        // 기본 데이터 준비
        User savedUser = userRepository.save(new User(
                null, "reward_user@test.com", "pass", "usr", "후원자", "010-1234-5678", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        Category savedCategory = categoryRepository.save(new Category(
                null, null, "테스트 카테고리", 1, 1, true
        ));

        savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "테스트 프로젝트", "[]",
                new BigDecimal("1000000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(30),
                ProjectStatus.ONGOING, LocalDateTime.now()
        ));
    }

    @Test
    void 리워드_목록_조회_테스트() throws Exception {
        // 리워드 미리 생성
        rewardRepository.save(new Reward(
                null, savedProject.id(), "리워드1", "설명1", new BigDecimal("10000"), 100, LocalDateTime.now()
        ));
        rewardRepository.save(new Reward(
                null, savedProject.id(), "리워드2", "설명2", new BigDecimal("20000"), 200, LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(get("/api/projects/{projectId}/rewards", savedProject.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<UserRewardsFetchResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("리워드 목록 조회에 성공했습니다.");
        assertThat(apiResult.data().rewards()).hasSize(2);
    }
}
