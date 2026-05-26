package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryRepository;
import io.github.authservice.crowdfund.domain.payment.Payment;
import io.github.authservice.crowdfund.domain.payment.PaymentRepository;
import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.authservice.crowdfund.domain.pledge.Pledge;
import io.github.authservice.crowdfund.domain.pledge.PledgeRepository;
import io.github.authservice.crowdfund.domain.pledgeaddress.PledgeAddress;
import io.github.authservice.crowdfund.domain.pledgeaddress.PledgeAddressRepository;
import io.github.authservice.crowdfund.domain.project.Project;
import io.github.authservice.crowdfund.domain.project.ProjectRepository;
import io.github.authservice.crowdfund.domain.project.ProjectStatus;
import io.github.authservice.crowdfund.domain.reward.Reward;
import io.github.authservice.crowdfund.domain.reward.RewardRepository;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PledgeServiceTest {

    @Autowired
    private MockMvc mockMvc;

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
                null, "pledger@test.com", "pass", "pledge", "pledge", "010-1111-2222", "USER", LocalDateTime.now(), LocalDateTime.now(), null
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

        mockMvc.perform(post("/api/project/pledges/{userId}", savedUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("펀딩 후원이 성공하였습니다."))
                .andExpect(jsonPath("$.pledgeId").exists())
                .andDo(print());
    }

    @Test
    void 후원_목록_조회_테스트() throws Exception {
        Pledge savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), 10000L, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        mockMvc.perform(get("/api/admin/pledge"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("펀딩 리스트를 성공적으로 불러왔습니다."))
                .andExpect(jsonPath("$.pledges").isArray())
                .andExpect(jsonPath("$.pledges[?(@.id == %d)].userId".formatted(savedPledge.id())).value(savedUser.id().intValue()))
                .andExpect(jsonPath("$.pledges[?(@.id == %d)].userName".formatted(savedPledge.id())).value(savedUser.name()))
                .andExpect(jsonPath("$.pledges[?(@.id == %d)].projectId".formatted(savedPledge.id())).value(savedProject.id().intValue()))
                .andExpect(jsonPath("$.pledges[?(@.id == %d)].projectTitle".formatted(savedPledge.id())).value(savedProject.title()))
                .andExpect(jsonPath("$.pledges[?(@.id == %d)].amount".formatted(savedPledge.id())).value(10000))
                .andExpect(jsonPath("$.pledges[?(@.id == %d)].fulfillmentStatus".formatted(savedPledge.id())).value("READY"))
                .andDo(print());
    }

    @Test
    void 후원_상세_조회_테스트() throws Exception {
        Pledge savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), 10000L, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        paymentRepository.save(new Payment(
                null, savedPledge.id(), "CREDIT_CARD", 10000L, "PAID", LocalDateTime.now(), LocalDateTime.now()
        ));

        pledgeAddressRepository.save(new PledgeAddress(
                null, savedPledge.id(), savedUser.id(), "홍길동", "010-1234-5678", "12345", "서울특별시 강남구 테헤란로 123", "4층 개발팀", LocalDateTime.now(), LocalDateTime.now()
        ));

        mockMvc.perform(get("/api/pledges/{pledgeId}", savedPledge.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("후원 상세 정보를 성공적으로 불러왔습니다."))
                .andExpect(jsonPath("$.pledgeDetail.pledgeId").value(savedPledge.id()))
                .andExpect(jsonPath("$.pledgeDetail.projectTitle").value(savedProject.title()))
                .andExpect(jsonPath("$.pledgeDetail.rewardName").value(savedReward.title()))
                .andExpect(jsonPath("$.pledgeDetail.paymentMethod").value("신용카드"))
                .andExpect(jsonPath("$.pledgeDetail.shippingAddress.recipientName").value("홍길동"))
                .andExpect(jsonPath("$.pledgeDetail.shippingAddress.address").value("서울특별시 강남구 테헤란로 123"))
                .andExpect(jsonPath("$.pledgeDetail.shippingAddress.postalCode").value("배송 준비중"))
                .andDo(print());
    }

    @Test
    void 후원_취소_테스트() throws Exception {
        Pledge savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), 10000L, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        mockMvc.perform(delete("/api/pledges/{pledgeId}", savedPledge.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("펀딩 주문을 성공적으로 취소했습니다."))
                .andDo(print());
    }

    @Test
    void 보상_이행_상태_갱신_테스트() throws Exception {
        Pledge savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), 10000L, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        String updateRequest = """
                {
                    "fulfillmentStatus": "COMPLETED"
                }
                """;

        mockMvc.perform(patch("/api/pledges/{pledgeId}/fulfillment", savedPledge.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("보상 이행 상태가 변경되었습니다."))
                .andExpect(jsonPath("$.updatedInfo.fulfillmentStatus").value("COMPLETED"))
                .andExpect(jsonPath("$.updatedInfo.fulfilledAt").exists())
                .andDo(print());
    }

    @Test
    void 관리자_후원_상세_조회_테스트() throws Exception {
        Pledge savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), 10000L, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        paymentRepository.save(new Payment(
                null, savedPledge.id(), "CREDIT_CARD", 10000L, "PAID", LocalDateTime.now(), LocalDateTime.now()
        ));

        mockMvc.perform(get("/api/admin/pledge/{pledgeId}", savedPledge.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("관리자용 후원 상세 정보를 성공적으로 불러왔습니다."))
                .andExpect(jsonPath("$.adminPledgeDetail.pledgeId").value(savedPledge.id()))
                .andExpect(jsonPath("$.adminPledgeDetail.user.userId").value(savedUser.id()))
                .andExpect(jsonPath("$.adminPledgeDetail.user.name").value(savedUser.name()))
                .andExpect(jsonPath("$.adminPledgeDetail.payment.amount").value(10000))
                .andExpect(jsonPath("$.adminPledgeDetail.payment.paymentMethod").value("CREDIT_CARD"))
                .andExpect(jsonPath("$.adminPledgeDetail.project.projectId").value(savedProject.id()))
                .andExpect(jsonPath("$.adminPledgeDetail.project.projectTitle").value(savedProject.title()))
                .andDo(print());
    }
}
