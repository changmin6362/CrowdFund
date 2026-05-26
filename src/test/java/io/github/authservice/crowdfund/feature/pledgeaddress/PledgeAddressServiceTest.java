package io.github.authservice.crowdfund.feature.pledgeaddress;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import io.github.authservice.crowdfund.domain.useraddress.UserAddress;
import io.github.authservice.crowdfund.domain.useraddress.UserAddressRepository;
import io.github.authservice.crowdfund.feature.pledgeaddress.response.GetPledgeAddressResponse;
import io.github.authservice.crowdfund.feature.pledgeaddress.response.ReplacePledgeAddressResponse;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PledgeAddressServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private PledgeRepository pledgeRepository;

    @Autowired
    private PledgeAddressRepository pledgeAddressRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    private User savedUser;
    private Project savedProject;
    private Reward savedReward;
    private Pledge savedPledge;
    private PledgeAddress savedPledgeAddress;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        savedUser = userRepository.save(new User(
                null, "test@example.com", "password", "nickname", "name", "010-1234-5678", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        savedProject = projectRepository.save(new Project(
                null, 1, savedUser.id(), "프로젝트 제목", "{}", new BigDecimal("1000000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(30), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        savedReward = rewardRepository.save(new Reward(
                null, savedProject.id(), "리워드 제목", "리워드 설명", new BigDecimal("10000"), 100, LocalDateTime.now()
        ));

        savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), 10000L, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));

        savedPledgeAddress = pledgeAddressRepository.save(new PledgeAddress(
                null, savedPledge.id(), savedUser.id(), "수령인", "010-1111-2222", "12345", "기본주소", "상세주소", LocalDateTime.now(), LocalDateTime.now()
        ));
    }

    @Test
    void 후원_주소_조회_테스트() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/pledges/{pledgesId}/addresses", savedPledge.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<GetPledgeAddressResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("후원 주소 조회에 성공했습니다.");
        assertThat(apiResult.data().pledgeAddress().recipientName()).isEqualTo("수령인");
        assertThat(apiResult.data().pledgeAddress().addressMain()).isEqualTo("기본주소");
    }

    @Test
    void 후원_주소_교체_테스트() throws Exception {
        // 교체할 사용자의 새로운 주소 등록
        UserAddress newAddress = userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "새수령인", "010-9999-8888", "54321", "새로운주소", "새로운상세", false, LocalDateTime.now(), LocalDateTime.now()
        ));

        String request = """
                {
                    "addressId": %d
                }
                """.formatted(newAddress.id());

        MvcResult result = mockMvc.perform(post("/api/pledges/{pledgeId}/addresses", savedPledge.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<ReplacePledgeAddressResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("후원 주소 교체에 성공했습니다.");
        assertThat(apiResult.data().replacedPledgeAddress().recipientName()).isEqualTo("새수령인");
        assertThat(apiResult.data().replacedPledgeAddress().addressMain()).isEqualTo("새로운주소");
    }

    @Test
    void 존재하지_않는_후원_주소_조회_실패_테스트() throws Exception {
        mockMvc.perform(get("/api/pledges/{pledgesId}/addresses", 99999L))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
