package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryRepository;
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

    private User savedUser;
    private Project savedProject;
    private Reward savedReward;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        savedUser = userRepository.save(new User(
                null, "pledger@test.com", "pass", "pledger", "pledger", "010-1111-2222", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        Category savedCategory = categoryRepository.save(new Category(
                null, null, "테스트 카테고리", 1, 10, true
        ));

        savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "테스트 프로젝트", "내용", new BigDecimal("1000000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(30), ProjectStatus.ONGOING, LocalDateTime.now()
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
        pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), 10000L, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        mockMvc.perform(get("/api/admin/pledge"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("펀딩 리스트를 성공적으로 불러왔습니다."))
                .andExpect(jsonPath("$.pledges").isArray())
                .andExpect(jsonPath("$.pledges[0].userName").value(savedUser.name()))
                .andExpect(jsonPath("$.pledges[0].projectTitle").value(savedProject.title()))
                .andExpect(jsonPath("$.pledges[0].fulfillmentStatus").value("READY"))
                .andDo(print());
    }

    @Test
    void 후원_상세_조회_테스트() throws Exception {
        Pledge savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), 10000L, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        mockMvc.perform(get("/api/pledges/{pledgeId}", savedPledge.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("펀딩 상세 정보를 성공적으로 불러왔습니다."))
                .andExpect(jsonPath("$.pledgeDetail.id").value(savedPledge.id()))
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
}
