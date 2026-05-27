package io.github.authservice.crowdfund.feature.payment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryRepository;
import io.github.authservice.crowdfund.domain.payment.Payment;
import io.github.authservice.crowdfund.domain.payment.PaymentRepository;
import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.authservice.crowdfund.domain.pledge.Pledge;
import io.github.authservice.crowdfund.domain.pledge.PledgeRepository;
import io.github.authservice.crowdfund.domain.project.Project;
import io.github.authservice.crowdfund.domain.project.ProjectRepository;
import io.github.authservice.crowdfund.domain.project.ProjectStatus;
import io.github.authservice.crowdfund.domain.reward.Reward;
import io.github.authservice.crowdfund.domain.reward.RewardRepository;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.payment.dto.create.PaymentCreateRequest;
import io.github.authservice.crowdfund.feature.payment.dto.create.PaymentCreateResponse;
import io.github.authservice.crowdfund.feature.payment.dto.fetch.PaymentFetchResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
import io.github.authservice.crowdfund.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentRepository paymentRepository;

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

    private User savedUser;
    private Project savedProject;
    private Reward savedReward;
    private Pledge savedPledge;

    @BeforeEach
    void setup() {
        savedUser = userRepository.save(new User(
                null, "payment@test.com", "pass", "pynick", "pay", "010-9999-8888", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        Category category = categoryRepository.save(new Category(
                null, null, "결제 테스트 카테고리", 1, 10, true
        ));

        savedProject = projectRepository.save(new Project(
                null, category.id(), savedUser.id(), "결제 테스트 프로젝트", "[]", new BigDecimal("1000000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(30), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        savedReward = rewardRepository.save(new Reward(
                null, savedProject.id(), "리워드", "설명", new BigDecimal("50000"), 100, LocalDateTime.now()
        ));

        savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), 50000L, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));
    }

    @Test
    void 결제_생성_테스트() throws Exception {
        PaymentCreateRequest request = new PaymentCreateRequest(
                savedPledge.id(),
                "CREDIT_CARD",
                50000L
        );

        MvcResult result = mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        ApiResult<PaymentCreateResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("결제 요청에 성공했습니다.");
        assertThat(apiResult.data().paymentId()).isNotNull();
    }

    @Test
    void 결제_생성_금액불일치_실패_테스트() throws Exception {
        PaymentCreateRequest request = new PaymentCreateRequest(
                savedPledge.id(),
                "CREDIT_CARD",
                30000L // Pledge는 50000L
        );

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void 결제_조회_테스트() throws Exception {
        Payment payment = paymentRepository.save(new Payment(
                null, savedPledge.id(), "KAKAOPAY", 50000L, "PAID", LocalDateTime.now(), LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(get("/api/payments/pledge/{pledgeId}", savedPledge.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<PaymentFetchResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("결제 내역 조회에 성공했습니다.");
        assertThat(apiResult.data().paymentDetail().id()).isEqualTo(payment.id());
        assertThat(apiResult.data().paymentDetail().paymentMethod()).isEqualTo("KAKAOPAY");
    }

    @Test
    void 결제_취소_테스트() throws Exception {
        Payment payment = paymentRepository.save(new Payment(
                null, savedPledge.id(), "CREDIT_CARD", 50000L, "PAID", LocalDateTime.now(), LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(delete("/api/payments/{paymentId}", payment.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<Void> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("결제 취소에 성공했습니다.");

        // 상태 확인
        Payment canceled = paymentRepository.findById(payment.id()).orElseThrow();
        assertThat(canceled.status()).isEqualTo("CANCELED");
    }
}
