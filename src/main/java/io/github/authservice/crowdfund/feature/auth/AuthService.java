package io.github.authservice.crowdfund.feature.auth;

import io.github.authservice.crowdfund.feature.auth.request.LogoutRequest;
import io.github.authservice.crowdfund.feature.auth.request.SignInRequest;
import io.github.authservice.crowdfund.feature.auth.request.SignUpRequest;
import io.github.authservice.crowdfund.feature.auth.response.LogoutResponse;
import io.github.authservice.crowdfund.feature.auth.response.SignInResponse;
import io.github.authservice.crowdfund.feature.auth.response.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository repository;

    /**
     * 회원가입 도메인 로직
     *
     * @param request 회원가입에 사용할 정보
     * @return message
     */
    public SignUpResponse signup(SignUpRequest request) {
        return new SignUpResponse("회원가입에 성공했습니다.", repository.signup(request));
    }

    /**
     * 로그인 도메인 로직
     *
     * @param request 로그인에 사용할 정보
     * @return message, nickname, accessToken, refreshToken
     */
    public SignInResponse login(SignInRequest request) {
        return new SignInResponse("로그인에 성공했습니다.", repository.login(request));
    }

    /**
     * 로그아웃 도메인 로직
     * <p/>
     * 액세스 토큰을 헤더, 리프레시 토큰을 바디로 받아서 서버에서 로그아웃 처리
     *
     * @param request 로그아웃에 사용할 정보
     * @return message
     */
    public LogoutResponse logout(LogoutRequest request) {
        repository.logout(request);

        return new LogoutResponse("로그아웃에 성공했습니다.");
    }
}
