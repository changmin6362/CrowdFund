package io.github.crowdfund.feature.pledge.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.crowdfund.domain.category.Category;
import io.github.crowdfund.domain.category.CategoryRepository;
import io.github.crowdfund.domain.payment.Payment;
import io.github.crowdfund.domain.payment.PaymentRepository;
import io.github.crowdfund.domain.payment.PaymentStatus;
import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.Pledge;
import io.github.crowdfund.domain.pledge.PledgeRepository;
import io.github.crowdfund.domain.pledgeaddress.PledgeAddress;
import io.github.crowdfund.domain.pledgeaddress.PledgeAddressRepository;
import io.github.crowdfund.domain.project.Project;
import io.github.crowdfund.domain.project.ProjectRepository;
import io.github.crowdfund.domain.project.ProjectStatus;
import io.github.crowdfund.domain.reward.Reward;
import io.github.crowdfund.domain.reward.RewardRepository;
import io.github.crowdfund.domain.user.User;
import io.github.crowdfund.domain.user.UserRepository;
import io.github.crowdfund.feature.pledge.user.dto.create.UserPledgeCreateResponse;
import io.github.crowdfund.feature.pledge.user.dto.detail.UserPledgeDetailResponse;
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
class UserPledgeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PledgeRepository pledgeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PledgeAddressRepository pledgeAddressRepository;

    private User savedUser;
    private Project savedProject;
    private Reward savedReward;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        savedUser = userRepository.save(new User(
                null, "pledger@test.com", "pass", "pledge", "후원자", "010-1111-2222", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        Category savedCategory = categoryRepository.save(new Category(
                null, null, "테스트 카테고리", 1, 10, true
        ));

        savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "테스트 프로젝트", "[{\"type\":\"text\",\"content\":\"내용\"}]", new BigDecimal("1000000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(30), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        savedReward = rewardRepository.save(new Reward(
                null, savedProject.id(), "테스트 리워드", "리워드 설명", new BigDecimal("10000"), 100, LocalDateTime.now()
        ));
    }

    @Test
    void 후원_참여_테스트() throws Exception {
        String createRequest = """
                {
                    "project_id": %d,
                    "reward_id": %d
                }
                """.formatted(savedProject.id(), savedReward.id());

        MvcResult result = mockMvc.perform(post("/api/pledges/{userId}", savedUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        ApiResult<UserPledgeCreateResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("프로젝트 후원에 성공했습니다.");
        assertThat(apiResult.data().pledgeId()).isNotNull();
    }

    @Test
    void 후원_상세_조회_테스트() throws Exception {
        Pledge savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), new BigDecimal("10000"), FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        LocalDateTime now = LocalDateTime.now();
        paymentRepository.save(new Payment(
                null, savedPledge.id(), "CREDIT_CARD", new BigDecimal("10000"), PaymentStatus.PAID, now, now, now
        ));

        pledgeAddressRepository.save(new PledgeAddress(
                null, savedPledge.id(), savedUser.id(), "홍길동", "010-1234-5678", "12345", "서울특별시 강남구 테헤란로 123", "4층 개발팀", LocalDateTime.now(), LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(get("/api/pledges/{pledgeId}", savedPledge.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<UserPledgeDetailResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("후원 상세 조회에 성공했습니다.");
        assertThat(apiResult.data().pledgeDetail().pledgeId()).isEqualTo(savedPledge.id());
        assertThat(apiResult.data().pledgeDetail().projectTitle()).isEqualTo(savedProject.title());
    }

    @Test
    void 후원_취소_테스트() throws Exception {
        Pledge savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), new BigDecimal("10000"), FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(delete("/api/pledges/{pledgeId}", savedPledge.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<Void> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("후원 취소에 성공했습니다.");
    }
}
