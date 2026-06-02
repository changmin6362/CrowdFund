package io.github.crowdfund.feature.project.creator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.crowdfund.domain.category.Category;
import io.github.crowdfund.domain.category.CategoryRepository;
import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.Pledge;
import io.github.crowdfund.domain.pledge.PledgeRepository;
import io.github.crowdfund.domain.pledge.PledgeStatus;
import io.github.crowdfund.domain.pledgeaddress.PledgeAddress;
import io.github.crowdfund.domain.pledgeaddress.PledgeAddressRepository;
import io.github.crowdfund.domain.project.Project;
import io.github.crowdfund.domain.project.ProjectRepository;
import io.github.crowdfund.domain.project.ProjectStatus;
import io.github.crowdfund.domain.reward.Reward;
import io.github.crowdfund.domain.reward.RewardRepository;
import io.github.crowdfund.domain.user.User;
import io.github.crowdfund.domain.user.UserRepository;
import io.github.crowdfund.feature.project.creator.dto.create.CreatorProjectCreateResponse;
import io.github.crowdfund.feature.project.creator.dto.extract.CreatorShippingInfosExtractResponse;
import io.github.crowdfund.feature.project.creator.dto.fetch.CreatorProjectsFetchResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.github.crowdfund.utils.TestUtils;
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
    private RewardRepository rewardRepository;

    @Autowired
    private PledgeRepository pledgeRepository;

    @Autowired
    private PledgeAddressRepository pledgeAddressRepository;

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

        MvcResult result = mockMvc.perform(post("/api/creator/projects/{creatorId}", savedUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        ApiResult<CreatorProjectCreateResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("프로젝트 생성에 성공했습니다.");
        assertThat(apiResult.data().createdProjectId()).isNotNull();
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

        MvcResult result = mockMvc.perform(patch("/api/creator/projects/{projectId}", savedProject.id())
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

        MvcResult result = mockMvc.perform(delete("/api/creator/projects/{projectId}", savedProject.id()))
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

        MvcResult result = mockMvc.perform(get("/api/creator/projects/me/{userId}", savedUser.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<CreatorProjectsFetchResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("내 프로젝트 조회에 성공했습니다.");
        assertThat(apiResult.data().projects()).isNotEmpty();
    }

    @Test
    void 후원자_배송_정보_목록_조회_테스트() throws Exception {
        // 1. 프로젝트 생성
        Project savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "배송 정보 테스트 프로젝트", "[{\"type\":\"text\",\"content\":\"내용\"}]", new BigDecimal("1000000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(30), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        // 2. 리워드 생성
        Reward savedReward = rewardRepository.save(new Reward(
                null, savedProject.id(), "테스트 리워드", "리워드 설명", new BigDecimal("10000"), 100, LocalDateTime.now()
        ));

        // 3. 후원자 생성
        User pledger = userRepository.save(new User(
                null, "pledger_" + System.currentTimeMillis() + "@test.com", "pass", "후원자", "이순신", "010-3333-4444", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        // 4. 후원 생성
        Pledge savedPledge = pledgeRepository.save(new Pledge(
                null, pledger.id(), savedProject.id(), savedReward.id(), new BigDecimal("10000"), PledgeStatus.PAID, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        // 5. 배송지 정보 생성
        pledgeAddressRepository.save(new PledgeAddress(
                null, savedPledge.id(), pledger.id(), "이순신", "010-3333-4444", "12345", "서울특별시 중구 세종대로 110", "서울시청", LocalDateTime.now(), LocalDateTime.now()
        ));

        // 6. API 호출
        MvcResult result = mockMvc.perform(get("/api/creator/projects/{projectId}/shipping-infos", savedProject.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // 7. 검증
        ApiResult<CreatorShippingInfosExtractResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("배송 정보 조회에 성공했습니다.");
        assertThat(apiResult.data().shippingInfos()).isNotEmpty();
        assertThat(apiResult.data().shippingInfos().get(0).recipientName()).isEqualTo("이순신");
    }
}
