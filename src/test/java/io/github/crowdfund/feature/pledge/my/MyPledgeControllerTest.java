package io.github.crowdfund.feature.pledge.my;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.crowdfund.domain.category.Category;
import io.github.crowdfund.domain.category.CategoryRepository;
import io.github.crowdfund.domain.payment.Payment;
import io.github.crowdfund.domain.payment.PaymentMethod;
import io.github.crowdfund.domain.payment.PaymentRepository;
import io.github.crowdfund.domain.payment.PaymentStatus;
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
import io.github.crowdfund.feature.pledge.my.dto.create.MyPledgeCreateResponse;
import io.github.crowdfund.feature.pledge.my.dto.detail.MyPledgeDetailResponse;
import io.github.crowdfund.feature.pledge.my.dto.fetch.MyPledgesFetchResponse;
import io.github.crowdfund.feature.useraddress.UserAddressService;
import io.github.crowdfund.feature.useraddress.dto.create.UserAddressCreateRequest;
import io.github.crowdfund.global.common.ApiResult;
import io.github.crowdfund.global.security.SecurityUser;
import io.github.crowdfund.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MyPledgeControllerTest {

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

    @Autowired
    private UserAddressService userAddressService;

    private User savedUser;
    private Project savedProject;
    private Reward savedReward;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        savedUser = userRepository.save(new User(
                null, "pledger@test.com", "pass", "pledge", "후원자", "010-1111-2222", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        // SecurityContext 설정
        SecurityUser securityUser = new SecurityUser(savedUser.id(), savedUser.email(), savedUser.password(), savedUser.nickname(), savedUser.deletedAt(), Collections.emptyList());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities()));
        SecurityContextHolder.setContext(context);

        // 기본 배송지 등록 (후원하기 시 필수)
        userAddressService.create(savedUser.id(), new UserAddressCreateRequest("홍길동", "010-1234-5678", "12345", "기본주소", "상세주소"));

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
                    "projectId": %d,
                    "rewardId": %d
                }
                """.formatted(savedProject.id(), savedReward.id());

        MvcResult result = mockMvc.perform(post("/api/pledges/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        ApiResult<MyPledgeCreateResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("프로젝트 후원에 성공했습니다.");
        assertThat(apiResult.data().pledgeId()).isNotNull();
    }

    @Test
    void 후원_상세_조회_테스트() throws Exception {
        Pledge savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), new BigDecimal("10000"), PledgeStatus.PAID, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        LocalDateTime now = LocalDateTime.now();
        paymentRepository.save(new Payment(
                null, savedPledge.id(), PaymentMethod.CARD, new BigDecimal("10000"), PaymentStatus.PAID, now, now, now
        ));

        pledgeAddressRepository.save(new PledgeAddress(
                null, savedPledge.id(), savedUser.id(), "홍길동", "010-1234-5678", "12345", "서울특별시 강남구 테헤란로 123", "4층 개발팀", LocalDateTime.now(), LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(get("/api/pledges/me/{pledgeId}", savedPledge.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<MyPledgeDetailResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("내 후원 상세 조회에 성공했습니다.");
        assertThat(apiResult.data().myPledgeDetail().pledgeId()).isEqualTo(savedPledge.id());
        assertThat(apiResult.data().myPledgeDetail().projectTitle()).isEqualTo(savedProject.title());
        assertThat(apiResult.data().myPledgeDetail().amount()).isEqualByComparingTo(savedReward.price());
        assertThat(apiResult.data().myPledgeDetail().shippingAddress().recipientName()).isEqualTo("홍길동");
        assertThat(apiResult.data().myPledgeDetail().shippingAddress().pledgeAddressId()).isNotNull();
        assertThat(apiResult.data().myPledgeDetail().shippingAddress().addressCreatedAt()).isNotNull();
        assertThat(apiResult.data().myPledgeDetail().shippingAddress().addressUpdatedAt()).isNotNull();
    }

    @Test
    void 후원_취소_테스트() throws Exception {
        Pledge savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), new BigDecimal("10000"), PledgeStatus.PAID, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(delete("/api/pledges/me/{pledgeId}", savedPledge.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<Void> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("후원 취소에 성공했습니다.");
    }

    @Test
    void 내_후원_목록_조회_필터링_테스트() throws Exception {
        // 1. PAID, READY 상태 후원
        pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), new BigDecimal("10000"), PledgeStatus.PAID, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));
        // 2. REFUNDED 상태 후원
        pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), new BigDecimal("10000"), PledgeStatus.REFUNDED, FulfillmentStatus.READY, null, LocalDateTime.now().plusSeconds(1)
        ));

        // PledgeStatus 필터링 테스트 (REFUNDED만 조회)
        MvcResult refundedResult = mockMvc.perform(get("/api/pledges/me")
                        .param("pledgeStatus", "REFUNDED")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andReturn();

        ApiResult<MyPledgesFetchResponse> refundedApiResult = TestUtils.convertToApiResult(refundedResult, objectMapper, new TypeReference<>() {});
        assertThat(refundedApiResult.data().pledges()).hasSize(1);
        assertThat(refundedApiResult.data().pledges().get(0).status()).isEqualTo(PledgeStatus.REFUNDED);

        // FulfillmentStatus 필터링 테스트 (READY만 조회)
        MvcResult readyResult = mockMvc.perform(get("/api/pledges/me")
                        .param("fulfillmentStatus", "READY")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andReturn();

        ApiResult<MyPledgesFetchResponse> readyApiResult = TestUtils.convertToApiResult(readyResult, objectMapper, new TypeReference<>() {});
        assertThat(readyApiResult.data().pledges()).hasSize(2);
    }

    @Test
    void 종료된_프로젝트_후원_불가_테스트() throws Exception {
        Project completedProject = projectRepository.save(new Project(
                null, savedProject.categoryId(), savedUser.id(), "종료된 프로젝트", "[{\"type\":\"text\",\"content\":\"내용\"}]", new BigDecimal("1000000"), BigDecimal.ZERO, LocalDateTime.now().minusDays(1), ProjectStatus.COMPLETED, LocalDateTime.now()
        ));

        Reward completedReward = rewardRepository.save(new Reward(
                null, completedProject.id(), "리워드", "설명", new BigDecimal("10000"), 100, LocalDateTime.now()
        ));

        String createRequest = """
                {
                    "projectId": %d,
                    "rewardId": %d
                }
                """.formatted(completedProject.id(), completedReward.id());

        mockMvc.perform(post("/api/pledges/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void 재고가_없는_리워드_후원_불가_테스트() throws Exception {
        Reward noStockReward = rewardRepository.save(new Reward(
                null, savedProject.id(), "품절 리워드", "설명", new BigDecimal("10000"), 0, LocalDateTime.now()
        ));

        String createRequest = """
                {
                    "projectId": %d,
                    "rewardId": %d
                }
                """.formatted(savedProject.id(), noStockReward.id());

        mockMvc.perform(post("/api/pledges/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void 중복_후원_불가_테스트() throws Exception {
        // 이미 후원 기록 생성
        pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), savedReward.price(), PledgeStatus.PENDING, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        String createRequest = """
                {
                    "projectId": %d,
                    "rewardId": %d
                }
                """.formatted(savedProject.id(), savedReward.id());

        mockMvc.perform(post("/api/pledges/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
