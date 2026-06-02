package io.github.crowdfund.feature.payment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.crowdfund.domain.category.Category;
import io.github.crowdfund.domain.category.CategoryRepository;
import io.github.crowdfund.domain.payment.Payment;
import io.github.crowdfund.domain.payment.PaymentMethod;
import io.github.crowdfund.domain.payment.PaymentRepository;
import io.github.crowdfund.domain.payment.PaymentStatus;
import io.github.crowdfund.domain.paymenthistory.PaymentHistory;
import io.github.crowdfund.domain.paymenthistory.PaymentHistoryRepository;
import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.Pledge;
import io.github.crowdfund.domain.pledge.PledgeRepository;
import io.github.crowdfund.domain.pledge.PledgeStatus;
import io.github.crowdfund.domain.project.Project;
import io.github.crowdfund.domain.project.ProjectRepository;
import io.github.crowdfund.domain.project.ProjectStatus;
import io.github.crowdfund.domain.reward.Reward;
import io.github.crowdfund.domain.reward.RewardRepository;
import io.github.crowdfund.domain.user.User;
import io.github.crowdfund.domain.user.UserRepository;
import io.github.crowdfund.feature.payment.dto.create.PaymentCreateRequest;
import io.github.crowdfund.feature.payment.dto.create.PaymentCreateResponse;
import io.github.crowdfund.feature.payment.dto.detail.PaymentDetailResponse;
import io.github.crowdfund.feature.payment.dto.history.PaymentHistoryResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.github.crowdfund.utils.TestUtils;
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
import java.util.List;

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
    private PaymentHistoryRepository paymentHistoryRepository;

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
                null, savedUser.id(), savedProject.id(), savedReward.id(), new BigDecimal("50000"), PledgeStatus.PAID, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));
    }

    @Test
    void 결제_생성_테스트() throws Exception {
        PaymentCreateRequest request = new PaymentCreateRequest(
                savedPledge.id(),
                PaymentMethod.CARD,
                new BigDecimal("50000")
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
                PaymentMethod.CARD,
                new BigDecimal("30000") // Pledge는 50000L
        );

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void 결제_조회_테스트() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Payment payment = paymentRepository.save(new Payment(
                null, savedPledge.id(), PaymentMethod.KAKAOPAY, new BigDecimal("50000"), PaymentStatus.PAID, now, now, now
        ));

        MvcResult result = mockMvc.perform(get("/api/payments/pledge/{pledgeId}", savedPledge.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<PaymentDetailResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("결제 상세 조회에 성공했습니다.");
        assertThat(apiResult.data().paymentDetail().paymentId()).isEqualTo(payment.id());
        assertThat(apiResult.data().paymentDetail().paymentMethod()).isEqualTo(PaymentMethod.KAKAOPAY);
    }

    @Test
    void 결제_생성_이미존재_실패_테스트() throws Exception {
        // 이미 결제가 존재하는 상태로 만듦
        LocalDateTime now = LocalDateTime.now();
        paymentRepository.save(new Payment(
                null, savedPledge.id(), PaymentMethod.CARD, new BigDecimal("50000"), PaymentStatus.PAID, now, now, now
        ));

        PaymentCreateRequest request = new PaymentCreateRequest(
                savedPledge.id(),
                PaymentMethod.CARD,
                new BigDecimal("50000")
        );

        MvcResult result = mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        ApiResult<Void> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});
        assertThat(apiResult.message()).isEqualTo("이미 결제가 완료되었습니다.");
    }

    @Test
    void 결제_취소_테스트() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Payment payment = paymentRepository.save(new Payment(
                null, savedPledge.id(), PaymentMethod.CARD, new BigDecimal("50000"), PaymentStatus.PAID, now, now, now
        ));

        MvcResult result = mockMvc.perform(delete("/api/payments/{paymentId}", payment.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<Void> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("결제 취소에 성공했습니다.");

        // 상태 확인
        Payment canceled = paymentRepository.findById(payment.id()).orElseThrow();
        assertThat(canceled.status()).isEqualTo(PaymentStatus.CANCELED);
    }

    @Test
    void 결제_이력_조회_테스트() throws Exception {
        // 1. 결제 생성 (서비스를 통해 히스토리까지 자동 생성됨)
        PaymentCreateRequest request = new PaymentCreateRequest(
                savedPledge.id(),
                PaymentMethod.CARD,
                new BigDecimal("50000")
        );
        MvcResult createResult = mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        ApiResult<PaymentCreateResponse> createResponse = TestUtils.convertToApiResult(createResult, objectMapper, new TypeReference<>() {});
        Long paymentId = createResponse.data().paymentId();

        // 2. 이력 조회
        MvcResult historyResult = mockMvc.perform(get("/api/payments/{paymentId}/history", paymentId))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<PaymentHistoryResponse> apiResult = TestUtils.convertToApiResult(historyResult, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("결제 이력 조회에 성공했습니다.");
        assertThat(apiResult.data().paymentHistories()).isNotEmpty();
        assertThat(apiResult.data().paymentHistories().get(0).status()).isEqualTo(PaymentStatus.PAID);
    }
}
