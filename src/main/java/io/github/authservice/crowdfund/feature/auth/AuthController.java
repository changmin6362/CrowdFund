package io.github.authservice.crowdfund.feature.auth;

import io.github.authservice.crowdfund.feature.auth.request.LogoutRequest;
import io.github.authservice.crowdfund.feature.auth.request.SignInRequest;
import io.github.authservice.crowdfund.feature.auth.request.SignUpRequest;
import io.github.authservice.crowdfund.feature.auth.response.LogoutResponse;
import io.github.authservice.crowdfund.feature.auth.response.SignInResponse;
import io.github.authservice.crowdfund.feature.auth.response.SignUpResponse;
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
    public SignUpResponse signUp(@Valid @RequestBody SignUpRequest request) {
        return service.signup(request);
    }

    /**
     * 로그인
     *
     * @param request 로그인 요청 정보
     * @return message, access token, refresh token
     */
    @PostMapping("signin")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponse signIn(@Valid @RequestBody SignInRequest request) {
        return service.login(request);
    }

    /**
     * 로그아웃
     *
     * @param request 로그아웃 요청 정보
     * @return message
     */
    @PostMapping("logout")
    @ResponseStatus(HttpStatus.OK)
    public LogoutResponse logout(@Valid @RequestBody LogoutRequest request) {
        return service.logout(request);
    }
}
