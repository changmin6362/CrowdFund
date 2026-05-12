package io.github.authservice.crowdfund.feature.auth;

import io.github.authservice.crowdfund.domain.user.UserRepository;
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

    private final UserRepository repository;

    /**
     * 회원가입 도메인 로직
     *
     * @param request 회원가입에 사용할 정보
     * @return message
     */
    public SignUpResponse signup(SignUpRequest request) {
        // User savedUser = repository.save(request);
        //         return new SignUpResponse("회원가입에 성공했습니다.", repository.signup(request));
        return new SignUpResponse("회원가입 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 로그인 도메인 로직
     *
     * @param request 로그인에 사용할 정보
     * @return message, accessToken, refreshToken
     */
    public SignInResponse login(SignInRequest request) {
        // TODO: 로그인 로직 구현 (비밀번호 확인, 토큰 생성 등)
        // return new SignInResponse("로그인에 성공했습니다.", repository.login(request));
        return new SignInResponse("로그인 기능은 아직 구현되지 않았습니다.", null, null);
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
        // TODO: 로그아웃 로직 구현 (토큰 무효화 등)
        // repository.logout(request);

        // return new LogoutResponse("로그아웃에 성공했습니다.");
        return new LogoutResponse("로그아웃 기능은 아직 구현되지 않았습니다.");
    }
}
