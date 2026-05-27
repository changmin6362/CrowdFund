package io.github.authservice.crowdfund.feature.auth;

import io.github.authservice.crowdfund.feature.auth.dto.logout.LogoutRequest;
import io.github.authservice.crowdfund.feature.auth.dto.signin.SignInRequest;
import io.github.authservice.crowdfund.feature.auth.dto.signup.SignUpRequest;
import io.github.authservice.crowdfund.feature.auth.dto.signin.SignInResponse;
import io.github.authservice.crowdfund.feature.auth.dto.signup.SignUpResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
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
    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return ApiResult.success("회원가입에 성공했습니다.", service.signup(request));
    }

    /**
     * 로그인
     *
     * @param request 로그인 요청 정보
     * @return message, access token, refresh token
     */
    @PostMapping("signin")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {
        return ApiResult.success("로그인에 성공했습니다.", service.login(request));
    }

    /**
     * 로그아웃
     *
     * @param request 로그아웃 요청 정보
     * @return message
     */
    @PostMapping("logout")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> logout(@Valid @RequestBody LogoutRequest request) {
        service.logout(request);

        return ApiResult.success("로그아웃에 성공했습니다.");
    }
}
