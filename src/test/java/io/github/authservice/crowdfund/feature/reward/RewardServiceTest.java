package io.github.authservice.crowdfund.feature.reward;

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
import io.github.authservice.crowdfund.feature.reward.response.*;
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
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RewardServiceTest {

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
                null, "test@test.com", "pass", "tester", "홍길동", "010-1234-5678", "USER", LocalDateTime.now(), LocalDateTime.now(), null
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
    void 리워드_생성_테스트() throws Exception {
        String createRequest = """
                {
                    "title": "슈퍼 얼리버드",
                    "description": "가장 먼저 후원해주시는 분들을 위한 혜택",
                    "price": 10000,
                    "stock": 50
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/projects/{projectId}/rewards", savedProject.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        ApiResult<CreateRewardResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("리워드 등록에 성공했습니다.");
        assertThat(apiResult.data().createdReward().title()).isEqualTo("슈퍼 얼리버드");
        assertThat(apiResult.data().createdReward().price()).isEqualByComparingTo("10000");
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

        ApiResult<GetRewardsResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("리워드 목록 조회에 성공했습니다.");
        assertThat(apiResult.data().rewards()).hasSize(2);
    }

    @Test
    void 리워드_수정_테스트() throws Exception {
        Reward savedReward = rewardRepository.save(new Reward(
                null, savedProject.id(), "기존 제목", "기존 설명", new BigDecimal("10000"), 100, LocalDateTime.now()
        ));

        String patchRequest = """
                {
                    "title": "수정된 제목",
                    "description": "수정된 설명",
                    "price": 15000,
                    "stock": 50
                }
                """;

        MvcResult result = mockMvc.perform(patch("/api/rewards/{rewardId}", savedReward.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<PatchRewardResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("리워드 수정에 성공했습니다.");
        assertThat(apiResult.data().patchedReward().title()).isEqualTo("수정된 제목");
        assertThat(apiResult.data().patchedReward().price()).isEqualByComparingTo("15000");
        assertThat(apiResult.data().patchedReward().stock()).isEqualTo(50);
    }

    @Test
    void 리워드_삭제_테스트() throws Exception {
        Reward savedReward = rewardRepository.save(new Reward(
                null, savedProject.id(), "삭제할 리워드", "설명", new BigDecimal("10000"), 100, LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(delete("/api/rewards/{rewardId}", savedReward.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<DeleteRewardResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("리워드 삭제에 성공했습니다.");
        assertThat(apiResult.data().deletedRewardId()).isEqualTo(savedReward.id());
    }

    @Test
    void 리워드_조회_실패_테스트() throws Exception {
        mockMvc.perform(patch("/api/rewards/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "실패 테스트",
                                    "description": "설명",
                                    "price": 1000,
                                    "stock": 10
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
