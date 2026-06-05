package io.github.crowdfund.feature.user;

import io.github.crowdfund.feature.user.dto.fetch.UserFetchResponse;
import io.github.crowdfund.feature.user.dto.update.UserUpdateRequest;
import io.github.crowdfund.feature.user.dto.view.UserViewResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
@Validated
@Tag(name = "02. User", description = "사용자 관련 API")
public class UserController {

    private final UserService service;

    /**
     * 내 닉네임 조회
     *
     * @param userDetails 인증된 사용자 정보
     * @return message, nickname
     */
    @Operation(summary = "내 닉네임 조회")
    @ApiResponse(responseCode = "200", description = "내 닉네임 조회 성공 응답 예시")
    @GetMapping("/nickname")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserViewResponse> view(@AuthenticationPrincipal UserDetails userDetails) {
        // userDetails.getUsername() 은 email임
        return ApiResult.success("내 닉네임 조회에 성공했습니다.", service.viewByEmail(userDetails.getUsername()));
    }

    /**
     * 내 정보 조회
     *
     * @param userDetails 인증된 사용자 정보
     * @return message, user
     */
    @Operation(summary = "내 정보 조회")
    @ApiResponse(responseCode = "200", description = "내 정보 조회 성공 응답 예시")
    @GetMapping("/data")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserFetchResponse> fetch(@AuthenticationPrincipal UserDetails userDetails) {
        return ApiResult.success("내 정보 조회에 성공했습니다.", service.fetchByEmail(userDetails.getUsername()));
    }

    /**
     * 내 정보 수정
     *
     * @param userDetails 인증된 사용자 정보
     * @param request     수정할 데이터
     * @return message
     */
    @Operation(summary = "내 정보 수정")
    @ApiResponse(responseCode = "200", description = "내 정보 수정 성공 응답 예시")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserFetchResponse> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        return ApiResult.success("내 정보 수정에 성공했습니다.", service.updateByEmail(userDetails.getUsername(), request));
    }

    /**
     * 회원 탈퇴
     *
     * @param userDetails 인증된 사용자 정보
     * @return message
     */
    @Operation(summary = "회원 탈퇴")
    @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공 응답 예시")
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> delete(@AuthenticationPrincipal UserDetails userDetails) {
        service.deleteByEmail(userDetails.getUsername());
        return ApiResult.success("회원 탈퇴에 성공했습니다.");
    }
}
