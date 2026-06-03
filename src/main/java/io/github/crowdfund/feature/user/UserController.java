package io.github.crowdfund.feature.user;

import io.github.crowdfund.feature.user.dto.update.UserUpdateRequest;
import io.github.crowdfund.feature.user.dto.fetch.UserFetchResponse;
import io.github.crowdfund.feature.user.dto.view.UserViewResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
@Validated
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService service;

    /**
     * 내 닉네임 조회
     *
     * @param userId 사용자 ID
     * @return message, nickname
     */
    @Operation(summary = "내 닉네임 조회")
    @ApiResponse(responseCode = "200", description = "내 닉네임 조회 성공 응답 예시")
    @GetMapping("/nickname/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserViewResponse> view(@PathVariable Long userId) {
        return ApiResult.success("내 닉네임 조회에 성공했습니다.", service.view(userId));
    }

    /**
     * 내 정보 조회
     *
     * @param userId 사용자 ID
     * @return message, user
     */
    @Operation(summary = "내 정보 조회")
    @ApiResponse(responseCode = "200", description = "내 정보 조회 성공 응답 예시")
    @GetMapping("/data/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserFetchResponse> fetch(@PathVariable Long userId) {
        return ApiResult.success("내 정보 조회에 성공했습니다.", service.fetch(userId));
    }

    /**
     * 내 정보 수정
     *
     * @param userId  사용자 ID
     * @param request 수정할 데이터
     * @return message
     */
    @Operation(summary = "내 정보 수정")
    @ApiResponse(responseCode = "200", description = "내 정보 수정 성공 응답 예시")
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> update(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequest request) {
        service.update(userId, request);

        return ApiResult.success("내 정보 수정에 성공했습니다.");
    }

    /**
     * 회원 탈퇴
     *
     * @param userId 사용자 ID
     * @return message
     */
    @Operation(summary = "회원 탈퇴")
    @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공 응답 예시")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> delete(@PathVariable Long userId) {
        service.delete(userId);

        return ApiResult.success("회원 탈퇴에 성공했습니다.");
    }
}
