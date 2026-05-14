package io.github.authservice.crowdfund.feature.user;

import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.user.response.GetUserNickNameResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());
    }

    @Test
    void 내_닉네임_조회_테스트() throws Exception {
        // 1. 테스트 데이터 삽입
        User savedUser = userRepository.save(new User(
                null, "test@test.com", "pass", "tester", "name", "010-1234-5678", "USER", LocalDateTime.now()
        ));

        // 2. MockMvc를 이용한 요청 및 결과 출력
        mockMvc.perform(get("/api/users/me/nickname/{userId}", savedUser.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value("tester"))
                .andDo(print());
    }

    @Test
    void 내_정보_조회_테스트() throws Exception {
        User savedUser = userRepository.save(new User(
                null, "data@test.com", "pass", "nick", "홍길동", "010-1111-2222", "USER", LocalDateTime.now()
        ));

        mockMvc.perform(get("/api/users/me/data/{userId}", savedUser.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email").value("data@test.com"))
                .andExpect(jsonPath("$.user.nickname").value("nick"))
                .andDo(print());
    }

    @Test
    void 내_정보_수정_테스트() throws Exception {
        User savedUser = userRepository.save(new User(
                null, "update@test.com", "pass", "old", "old", "010-0000-0000", "USER", LocalDateTime.now()
        ));

        String updateRequest = """
                {
                    "nickname": "new_nick",
                    "name": "new",
                    "phone": "010-9999-9999"
                }
                """;

        mockMvc.perform(put("/api/users/me/{userId}", savedUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("내 정보 수정에 성공했습니다."))
                .andDo(print());
    }

    @Test
    void 회원_탈퇴_테스트() throws Exception {
        User savedUser = userRepository.save(new User(
                null, "del@test.com", "pass", "del", "del", "010-4444-5555", "USER", LocalDateTime.now()
        ));

        mockMvc.perform(delete("/api/users/me/{userId}", savedUser.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원 탈퇴에 성공했습니다."))
                .andDo(print());
    }

    @Test
    void 내_후원_목록_조회_테스트() throws Exception {
        User savedUser = userRepository.save(new User(
                null, "pledge@test.com", "pass", "pl", "pl", "010-7777-8888", "USER", LocalDateTime.now()
        ));

        mockMvc.perform(get("/api/users/me/pledges/{userId}", savedUser.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("내가 후원한 프로젝트 목록 조회에 성공했습니다."))
                .andDo(print());
    }

    @Test
    void 사용자_조회_실패_테스트() throws Exception {
        mockMvc.perform(get("/api/users/me/nickname/9999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("존재하지 않는 사용자입니다."))
                .andDo(print());
    }
}
