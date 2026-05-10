package io.github.authservice.crowdfund.feature.user;

import io.github.authservice.crowdfund.feature.user.request.UserUpdateRequest;
import io.github.authservice.crowdfund.feature.user.response.DeleteUserResponse;
import io.github.authservice.crowdfund.feature.user.response.GetUserDataResponse;
import io.github.authservice.crowdfund.feature.user.response.GetUserNickNameResponse;
import io.github.authservice.crowdfund.feature.user.response.UpdateUserDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    /**
     * 내 닉네임 조회
     *
     * @param userId 사용자 ID
     * @return message, nickname
     */
    @GetMapping("/me/{userId}/nickname")
    @ResponseStatus(HttpStatus.OK)
    public GetUserNickNameResponse getUserNickName(@PathVariable String userId) {
        return service.getUserNickName(userId);
    }

    /**
     * 내 정보 조회
     *
     * @param userId 사용자 ID
     * @return message, user
     */
    @GetMapping("/me/{userId}/data")
    @ResponseStatus(HttpStatus.OK)
    public GetUserDataResponse getUserData(@PathVariable String userId) {
        return service.getUserData(userId);
    }

    /**
     * 내 정보 수정
     *
     * @param userId  사용자 ID
     * @param request 수정할 데이터
     * @return message
     */
    @PutMapping("/me/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateUserDataResponse updateUserData(@PathVariable String userId, @Valid @RequestBody UserUpdateRequest request) {
        return service.updateUserData(userId, request);
    }

    /**
     * 회원 탈퇴
     *
     * @param userId 사용자 ID
     * @return message
     */
    @DeleteMapping("/me/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteUserResponse deleteUser(@PathVariable String userId) {
        return service.deleteUser(userId);
    }
}
