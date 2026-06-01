package io.github.crowdfund.feature.auth;

import io.github.crowdfund.feature.auth.dto.logout.AuthLogoutRequest;
import io.github.crowdfund.feature.auth.dto.signin.AuthSignInRequest;
import io.github.crowdfund.feature.auth.dto.signup.AuthSignUpRequest;
import io.github.crowdfund.feature.auth.dto.signin.AuthSignInResponse;
import io.github.crowdfund.feature.auth.dto.signup.AuthSignUpResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    /**
     * 회원가입
     *
     * @param request 회원가입 요청 정보
     * @return message, userId
     */
    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "201", description = "회원가입 성공 응답 예시")
    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<AuthSignUpResponse> signUp(@Valid @RequestBody AuthSignUpRequest request) {
        return ApiResult.success("회원가입에 성공했습니다.", service.signup(request));
    }

    /**
     * 로그인
     *
     * @param request 로그인 요청 정보
     * @return message, access token, refresh token, userInfo
     */
    @Operation(summary = "로그인")
    @ApiResponse(responseCode = "200", description = "로그인 성공 응답 예시")
    @PostMapping("signin")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<AuthSignInResponse> signIn(@Valid @RequestBody AuthSignInRequest request) {
        return ApiResult.success("로그인에 성공했습니다.", service.login(request));
    }

    /**
     * 로그아웃
     *
     * @param request 로그아웃 요청 정보
     * @return message
     */
    @Operation(summary = "로그아웃")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공 응답 예시")
    @PostMapping("logout")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> logout(@Valid @RequestBody AuthLogoutRequest request) {
        service.logout(request);

        return ApiResult.success("로그아웃에 성공했습니다.");
    }
}
