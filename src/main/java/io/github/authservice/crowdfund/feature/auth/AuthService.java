package io.github.authservice.crowdfund.feature.auth;

import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.auth.dto.logout.AuthLogoutRequest;
import io.github.authservice.crowdfund.feature.auth.dto.signin.AuthSignInRequest;
import io.github.authservice.crowdfund.feature.auth.dto.signup.AuthSignUpRequest;
import io.github.authservice.crowdfund.feature.auth.dto.signin.AuthSignInResponse;
import io.github.authservice.crowdfund.feature.auth.dto.signup.AuthSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository repository;

    /**
     * 회원가입 도메인 로직
     */
    @Transactional
    public AuthSignUpResponse signup(AuthSignUpRequest request) {
        // User savedUser = repository.save(request);
        //         return ApiResponse.success("회원가입에 성공했습니다.", new SignUpResponse(repository.signup(request)));
        return new AuthSignUpResponse(null);
    }

    /**
     * 로그인 도메인 로직
     */
    @Transactional
    public AuthSignInResponse login(AuthSignInRequest request) {
        // TODO: 로그인 로직 구현 (비밀번호 확인, 토큰 생성 등)
        // return ApiResponse.success("로그인에 성공했습니다.", new SignInResponse(accessToken, refreshToken));
        return new AuthSignInResponse(null, null);
    }

    /**
     * 로그아웃 도메인 로직
     */
    @Transactional
    public Void logout(AuthLogoutRequest request) {
        // TODO: 로그아웃 로직 구현 (토큰 무효화 등)
        // repository.logout(request);

        // return ApiResponse.success("로그아웃에 성공했습니다.");
        return null;
    }
}
