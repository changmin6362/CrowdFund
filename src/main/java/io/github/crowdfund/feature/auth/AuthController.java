package io.github.crowdfund.feature.auth;

import io.github.crowdfund.feature.auth.dto.login.LoginRequest;
import io.github.crowdfund.feature.auth.dto.login.LoginResponse;
import io.github.crowdfund.feature.auth.dto.signup.SignUpRequest;
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
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final AuthService service;

    /**
     * 회원가입
     *
     * @param request 회원가입 요청 정보
     * @return message
     */
    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "201", description = "회원가입 성공 응답 예시")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<Void> signUp(@Valid @RequestBody SignUpRequest request) {
        service.signup(request);
        return ApiResult.success("회원가입에 성공했습니다.");
    }

    /**
     * 로그인
     *
     * @param request 로그인 요청 정보
     * @return message, access token, user info
     */
    @Operation(summary = "로그인")
    @ApiResponse(responseCode = "200", description = "로그인 성공 응답 예시")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResult.success("로그인에 성공했습니다.", service.login(request));
    }
}
