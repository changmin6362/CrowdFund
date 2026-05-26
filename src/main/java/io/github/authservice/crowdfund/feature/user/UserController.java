package io.github.authservice.crowdfund.feature.user;

import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.authservice.crowdfund.feature.user.request.UserUpdateRequest;
import io.github.authservice.crowdfund.feature.user.response.GetMyPledgeListResponse;
import io.github.authservice.crowdfund.feature.user.response.GetUserDataResponse;
import io.github.authservice.crowdfund.feature.user.response.GetUserNickNameResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    /**
     * 내 닉네임 조회
     *
     * @param userId 사용자 ID
     * @return message, nickname
     */
    @Operation(summary = "내 닉네임 조회", description = "사용자의 닉네임을 조회합니다.")
    @GetMapping("/nickname/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetUserNickNameResponse> getUserNickName(@PathVariable Long userId) {
        return ApiResult.success("내 닉네임 조회에 성공했습니다.", service.getUserNickName(userId));
    }

    /**
     * 내 정보 조회
     *
     * @param userId 사용자 ID
     * @return message, user
     */
    @Operation(summary = "내 정보 조회", description = "사용자의 정보를 조회합니다.")
    @GetMapping("/data/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetUserDataResponse> getUserData(@PathVariable Long userId) {
        return ApiResult.success("내 정보 조회에 성공했습니다.", service.getUserData(userId));
    }

    /**
     * 내 정보 수정
     *
     * @param userId  사용자 ID
     * @param request 수정할 데이터
     * @return message
     */
    @Operation(summary = "내 정보 수정", description = "사용자의 정보를 수정합니다.")
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> updateUserData(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequest request) {
        service.updateUserData(userId, request);

        return ApiResult.success("내 정보 수정에 성공했습니다.");
    }

    /**
     * 회원 탈퇴
     *
     * @param userId 사용자 ID
     * @return message
     */
    @Operation(summary = "회원 탈퇴", description = "사용자를 탈퇴시킵니다.")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> deleteUser(@PathVariable Long userId) {
        service.deactivateUser(userId);

        return ApiResult.success("회원 탈퇴에 성공했습니다.");
    }

    /**
     * 내가 후원한 프로젝트 목록 조회
     *
     * @param userId 사용자 ID
     * @param status 후원 상태 필터 (null인 경우 모든 상태 조회)
     * @return message, pledgeList
     */
    @Operation(summary = "내가 후원한 프로젝트 목록 조회", description = "사용자가 참여한 후원 내역을 조회합니다.")
    @GetMapping("/pledges/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetMyPledgeListResponse> getMyPledgeList(
            @PathVariable Long userId,
            @RequestParam(required = false) FulfillmentStatus status
    ) {
        return ApiResult.success("내가 후원한 프로젝트 목록 조회에 성공했습니다.", service.getMyPledgeList(userId, status));
    }
}
