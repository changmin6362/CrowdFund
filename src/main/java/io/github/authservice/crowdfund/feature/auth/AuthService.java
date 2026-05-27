package io.github.authservice.crowdfund.feature.auth;

import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.auth.dto.logout.LogoutRequest;
import io.github.authservice.crowdfund.feature.auth.dto.signin.SignInRequest;
import io.github.authservice.crowdfund.feature.auth.dto.signup.SignUpRequest;
import io.github.authservice.crowdfund.feature.auth.dto.signin.SignInResponse;
import io.github.authservice.crowdfund.feature.auth.dto.signup.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;

    /**
     * 회원가입 도메인 로직
     */
    public SignUpResponse signup(SignUpRequest request) {
        // User savedUser = repository.save(request);
        //         return ApiResponse.success("회원가입에 성공했습니다.", new SignUpResponse(repository.signup(request)));
        return new SignUpResponse(null);
    }

    /**
     * 로그인 도메인 로직
     */
    public SignInResponse login(SignInRequest request) {
        // TODO: 로그인 로직 구현 (비밀번호 확인, 토큰 생성 등)
        // return ApiResponse.success("로그인에 성공했습니다.", new SignInResponse(accessToken, refreshToken));
        return new SignInResponse(null, null);
    }

    /**
     * 로그아웃 도메인 로직
     */
    public Void logout(LogoutRequest request) {
        // TODO: 로그아웃 로직 구현 (토큰 무효화 등)
        // repository.logout(request);

        // return ApiResponse.success("로그아웃에 성공했습니다.");
        return null;
    }
}
