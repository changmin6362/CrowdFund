package io.github.crowdfund.feature.pledgeaddress;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import io.github.crowdfund.domain.useraddress.UserAddress;
import io.github.crowdfund.domain.useraddress.UserAddressRepository;
import io.github.crowdfund.feature.pledgeaddress.dto.replace.PledgeAddressReplaceResponse;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PledgeAddressControllerTest {

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

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        savedUser = userRepository.save(new User(
                null, "test@example.com", "password", "nickname", "name", "010-1234-5678", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        // SecurityContext 설정
        SecurityUser securityUser = new SecurityUser(savedUser.id(), savedUser.email(), savedUser.password(), savedUser.nickname(), savedUser.deletedAt(), Collections.emptyList());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities()));
        SecurityContextHolder.setContext(context);

        savedProject = projectRepository.save(new Project(
                null, 1, savedUser.id(), "프로젝트 제목", "{}", new BigDecimal("1000000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(30), ProjectStatus.ONGOING, LocalDateTime.now()
        ));

        savedReward = rewardRepository.save(new Reward(
                null, savedProject.id(), "리워드 제목", "리워드 설명", new BigDecimal("10000"), 100, LocalDateTime.now()
        ));

        savedPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), new BigDecimal("10000"), PledgeStatus.PAID, FulfillmentStatus.READY, null, LocalDateTime.now()
        ));
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

        MvcResult result = mockMvc.perform(put("/api/pledges/{pledgeId}/address", savedPledge.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<PledgeAddressReplaceResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("참여한 후원의 배송 정보 교체에 성공했습니다.");
        assertThat(apiResult.data().replacedPledgeAddress().recipientName()).isEqualTo("새수령인");
        assertThat(apiResult.data().replacedPledgeAddress().addressMain()).isEqualTo("새로운주소");
    }

    @Test
    void 이미_이행된_후원_주소_교체_실패_테스트() throws Exception {
        // 이행 완료 상태로 변경
        Pledge fulfilledPledge = pledgeRepository.save(new Pledge(
                null, savedUser.id(), savedProject.id(), savedReward.id(), new BigDecimal("10000"), PledgeStatus.PAID, FulfillmentStatus.FULFILLED, LocalDateTime.now(), LocalDateTime.now()
        ));

        pledgeAddressRepository.save(new PledgeAddress(
                null, fulfilledPledge.id(), savedUser.id(), "수령인", "010-1111-2222", "12345", "기본주소", "상세주소", LocalDateTime.now(), LocalDateTime.now()
        ));

        UserAddress newAddress = userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "새수령인", "010-9999-8888", "54321", "새로운주소", "새로운상세", false, LocalDateTime.now(), LocalDateTime.now()
        ));

        String request = """
                {
                    "addressId": %d
                }
                """.formatted(newAddress.id());

        mockMvc.perform(put("/api/pledges/{pledgeId}/address", fulfilledPledge.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void 타인의_주소로_교체_실패_테스트() throws Exception {
        // 다른 사용자 생성
        User anotherUser = userRepository.save(new User(
                null, "another" + (System.currentTimeMillis() % 1000000) + "@ex.com", "password", "another", "타인", "010-0000-0000", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        // 다른 사용자의 주소 등록
        UserAddress anotherAddress = userAddressRepository.save(new UserAddress(
                null, anotherUser.id(), "타인수령인", "010-0000-0000", "00000", "타인주소", "타인상세", false, LocalDateTime.now(), LocalDateTime.now()
        ));

        String request = """
                {
                    "addressId": %d
                }
                """.formatted(anotherAddress.id());

        mockMvc.perform(put("/api/pledges/{pledgeId}/address", savedPledge.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void 주소_교체_변경사항_없음_실패_테스트() throws Exception {
        // 기존 주소와 동일한 정보를 가진 주소 등록
        UserAddress sameAddress = userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "수령인", "010-1111-2222", "12345", "기본주소", "상세주소", false, LocalDateTime.now(), LocalDateTime.now()
        ));

        String request = """
                {
                    "addressId": %d
                }
                """.formatted(sameAddress.id());

        mockMvc.perform(put("/api/pledges/{pledgeId}/address", savedPledge.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
