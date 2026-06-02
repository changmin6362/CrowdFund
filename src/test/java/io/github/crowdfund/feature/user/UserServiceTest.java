package io.github.crowdfund.feature.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.crowdfund.domain.user.User;
import io.github.crowdfund.domain.user.UserRepository;
import io.github.crowdfund.feature.user.dto.fetch.UserFetchResponse;
import io.github.crowdfund.feature.user.dto.view.UserViewResponse;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());
    }

    @Test
    void 내_닉네임_조회_테스트() throws Exception {
        // 1. 테스트 데이터 삽입
        User savedUser = userRepository.save(new User(
                null, "test@test.com", "pass", "tester", "홍길동", "010-1234-5678", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        // 2. MockMvc를 이용한 요청 및 결과 출력
        MvcResult result = mockMvc.perform(get("/api/users/me/nickname/{userId}", savedUser.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<UserViewResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("내 닉네임 조회에 성공했습니다.");
        assertThat(apiResult.data().nickname()).isEqualTo("tester");
    }

    @Test
    void 내_정보_조회_테스트() throws Exception {
        User savedUser = userRepository.save(new User(
                null, "data@test.com", "pass", "nick", "홍길동", "010-1111-2222", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        MvcResult result = mockMvc.perform(get("/api/users/me/data/{userId}", savedUser.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<UserFetchResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("내 정보 조회에 성공했습니다.");
        assertThat(apiResult.data().user().email()).isEqualTo("data@test.com");
        assertThat(apiResult.data().user().nickname()).isEqualTo("nick");
    }

    @Test
    void 내_정보_수정_테스트() throws Exception {
        User savedUser = userRepository.save(new User(
                null, "update@test.com", "pass", "old", "old", "010-0000-0000", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        String updateRequest = """
                {
                    "nickname": "new_nick",
                    "name": "new",
                    "phone": "010-9999-9999"
                }
                """;

        MvcResult result = mockMvc.perform(put("/api/users/me/{userId}", savedUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<Void> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("내 정보 수정에 성공했습니다.");
    }

    @Test
    void 회원_탈퇴_테스트() throws Exception {
        User savedUser = userRepository.save(new User(
                null, "del@test.com", "pass", "del", "del", "010-4444-5555", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        MvcResult result = mockMvc.perform(delete("/api/users/me/{userId}", savedUser.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<Void> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("회원 탈퇴에 성공했습니다.");
    }

    @Test
    void 사용자_조회_실패_테스트() throws Exception {
        mockMvc.perform(get("/api/users/me/nickname/9999"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
