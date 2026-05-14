package io.github.authservice.crowdfund.feature.user;

import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.user.response.GetUserNickNameResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void 내_닉네임_조회_도메인_로직_성공_테스트() {
        // 1. 테스트를 위해 User 데이터를 DB에 추가
        String targetNickname = "real_tester";
        User testUser = new User(
                null, // DB Auto Increment
                "real_db_test@test.com",
                "pass",
                targetNickname,
                "name",
                "010-1234-5678",
                "USER",
                LocalDateTime.now()
        );
        User savedUser = userRepository.save(testUser);
        Long userId = savedUser.id(); // 생성된 ID 추출

        // 2. 서비스 호출 (사용자 ID를 사용하여 닉네임 조회)
        GetUserNickNameResponse response = userService.getUserNickName(userId);

        // 3. 결과 확인 및 출력
        System.out.println("[DEBUG_LOG] 닉네임 조회 테스트 결과: " + response);
        assertEquals(targetNickname, response.nickname());
    }

    @Test
    void 내_닉네임_조회_도메인_로직_실패_테스트() {

    }

    @Test
    void 내_정보_조회_도메인_로직_성공_테스트() {

    }

    @Test
    void 내_정보_조회_도메인_로직_실패_테스트() {

    }

    @Test
    void 내_정보_수정_도메인_로직_성공_테스트() {
        // 1. 테스트용 데이터 삽입
        User testUser = new User(
                null,
                "update_test@test.com",
                "pass",
                "old_nick",
                "old",
                "010-0000-0000",
                "USER",
                LocalDateTime.now()
        );
        User savedUser = userRepository.save(testUser);
        Long userId = savedUser.id();

        // 2. 수정 요청 데이터 생성
        io.github.authservice.crowdfund.feature.user.request.UserUpdateRequest updateRequest =
                new io.github.authservice.crowdfund.feature.user.request.UserUpdateRequest(
                        "new_nick",
                        "new",
                        "010-9999-9999"
                );

        // 3. 서비스 호출
        var response = userService.updateUserData(userId, updateRequest);

        // 4. 결과 검증
        System.out.println("[DEBUG_LOG] 정보 수정 테스트 결과: " + response);
        assertEquals("내 정보 수정에 성공했습니다.", response.message());

        // DB에서 실제로 바뀌었는지 확인
        User updatedUser = userRepository.findById(userId).orElseThrow();
        assertEquals("new_nick", updatedUser.nickname());
        assertEquals("new", updatedUser.name());
        assertEquals("010-9999-9999", updatedUser.phone());
    }

    @Test
    void 회원_탈퇴_성공_테스트() {}

    @Test
    void 회원_탈퇴_실패_테스트() {}
}
