package io.github.authservice.crowdfund.feature.useraddress;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.domain.useraddress.UserAddress;
import io.github.authservice.crowdfund.domain.useraddress.UserAddressRepository;
import io.github.authservice.crowdfund.feature.useraddress.response.CreateUserAddressResponse;
import io.github.authservice.crowdfund.feature.useraddress.response.GetUserAddressesResponse;
import io.github.authservice.crowdfund.feature.useraddress.response.PatchUserAddressResponse;
import io.github.authservice.crowdfund.feature.useraddress.response.SetDefaultAddressResponse;
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

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Autowired
    private ObjectMapper objectMapper;

    private User savedUser;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        savedUser = userRepository.save(new User(
                null, "creator@test.com", "pass", "creator", "홍길동", "010-1111-2222", "USER", LocalDateTime.now(), LocalDateTime.now(), null
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

        MvcResult result = mockMvc.perform(post("/api/users/me/address/{userId}", savedUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        ApiResult<CreateUserAddressResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("배송지 등록에 성공했습니다.");
        assertThat(apiResult.data().addressId()).isNotNull();
    }

    @Test
    void 배송지_목록_조회_테스트() throws Exception {
        userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "홍길동", "010-1234-5678", "12345", "서울시", "상세주소", true, LocalDateTime.now(), LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(get("/api/users/me/addresses/{userId}", savedUser.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<GetUserAddressesResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("배송지 목록 조회에 성공했습니다.");
        assertThat(apiResult.data().addresses()).isNotEmpty();
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

        MvcResult result = mockMvc.perform(patch("/api/users/me/address/{addressId}", savedAddress.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<PatchUserAddressResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("배송지 수정에 성공했습니다.");
        assertThat(apiResult.data().updatedAddress().recipientName()).isEqualTo("수정이름");
    }

    @Test
    void 기본_배송지_설정_테스트() throws Exception {
        UserAddress address1 = userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "주소1", "010-1111-1111", "11111", "주소1", "상세1", true, LocalDateTime.now(), LocalDateTime.now()
        ));
        UserAddress address2 = userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "주소2", "010-2222-2222", "22222", "주소2", "상세2", false, LocalDateTime.now(), LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(patch("/api/users/me/address/{addressId}/default", address2.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<SetDefaultAddressResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("기본 배송지 변경에 성공했습니다.");
        assertThat(apiResult.data().defaultAddressResult().addressId()).isEqualTo(address2.id());
        assertThat(apiResult.data().defaultAddressResult().isDefault()).isTrue();
    }

    @Test
    void 배송지_삭제_테스트() throws Exception {
        // 기본 배송지는 삭제 불가하므로 비기본 배송지 생성
        UserAddress address = userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "삭제용", "010-0000-0000", "00000", "삭제주소", "상세", false, LocalDateTime.now(), LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(delete("/api/users/me/address/{addressId}", address.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<Void> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("배송지 삭제에 성공했습니다.");
    }

    @Test
    void 기본_배송지_삭제_실패_테스트() throws Exception {
        UserAddress defaultAddress = userAddressRepository.save(new UserAddress(
                null, savedUser.id(), "기본", "010-1111-1111", "11111", "주소", "상세", true, LocalDateTime.now(), LocalDateTime.now()
        ));

        mockMvc.perform(delete("/api/users/me/address/{addressId}", defaultAddress.id()))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
