package io.github.authservice.crowdfund.feature.useraddress;

import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.domain.useraddress.UserAddress;
import io.github.authservice.crowdfund.domain.useraddress.UserAddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserAddressServiceTest {

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private User savedUser;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        savedUser = userRepository.save(new User(
                null, "test@test.com", "pass", "tester", "테스터", "010-1111-2222", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));
    }

    @Test
    void 배송지_등록_테스트() throws Exception {
        String createRequest = """
                {
                    "recipientName": "홍길동",
                    "phone": "010-1234-5678",
                    "postalCode": "12345",
                    "addressMain": "서울시 강남구 테헤란로",
                    "addressDetail": "123번지 4층"
                }
                """;

        mockMvc.perform(post("/api/users/me/address/{userId}", savedUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("주소 추가에 성공했습니다."))
                .andExpect(jsonPath("$.addressId").exists())
                .andDo(print());
    }

    @Test
    void 배송지_목록_조회_테스트() throws Exception {
        userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "홍길동", "010-1234-5678", "12345", "서울시", "상세주소", true, LocalDateTime.now(), LocalDateTime.now()
        ));

        mockMvc.perform(get("/api/users/me/addresses/{userId}", savedUser.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("내 배송지 목록 조회 성공"))
                .andExpect(jsonPath("$.addresses").isArray())
                .andDo(print());
    }

    @Test
    void 배송지_수정_테스트() throws Exception {
        UserAddress savedAddress = userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "원래이름", "010-1111-2222", "11111", "원래주소", "상세", false, LocalDateTime.now(), LocalDateTime.now()
        ));

        String patchRequest = """
                {
                    "recipientName": "수정이름",
                    "phone": "010-9999-9999",
                    "postalCode": "54321",
                    "addressMain": "수정주소",
                    "addressDetail": "수정상세"
                }
                """;

        mockMvc.perform(patch("/api/users/me/address/{addressId}", savedAddress.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("주소 수정에 성공했습니다."))
                .andExpect(jsonPath("$.updatedAddress.recipientName").value("수정이름"))
                .andDo(print());
    }

    @Test
    void 기본_배송지_설정_테스트() throws Exception {
        UserAddress address1 = userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "주소1", "010-1111-1111", "11111", "주소1", "상세1", true, LocalDateTime.now(), LocalDateTime.now()
        ));
        UserAddress address2 = userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "주소2", "010-2222-2222", "22222", "주소2", "상세2", false, LocalDateTime.now(), LocalDateTime.now()
        ));

        mockMvc.perform(patch("/api/users/me/address/{addressId}/default", address2.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("기본 배송지 변경에 성공했습니다."))
                .andExpect(jsonPath("$.defaultAddressResult.addressId").value(address2.id()))
                .andExpect(jsonPath("$.defaultAddressResult.isDefault").value(true))
                .andDo(print());
    }

    @Test
    void 배송지_삭제_테스트() throws Exception {
        // 기본 배송지는 삭제 불가하므로 비기본 배송지 생성
        UserAddress address = userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "삭제용", "010-0000-0000", "00000", "삭제주소", "상세", false, LocalDateTime.now(), LocalDateTime.now()
        ));

        mockMvc.perform(delete("/api/users/me/address/{addressId}", address.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("주소 삭제에 성공했습니다."))
                .andDo(print());
    }

    @Test
    void 기본_배송지_삭제_실패_테스트() throws Exception {
        UserAddress defaultAddress = userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "기본", "010-1111-1111", "11111", "주소", "상세", true, LocalDateTime.now(), LocalDateTime.now()
        ));

        mockMvc.perform(delete("/api/users/me/address/{addressId}", defaultAddress.id()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("기본 배송지는 삭제할 수 없습니다. 다른 배송지를 기본으로 설정한 후 삭제해주세요."))
                .andDo(print());
    }
}
