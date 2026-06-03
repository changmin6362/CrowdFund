package io.github.crowdfund.feature.auth;

import io.github.crowdfund.domain.user.User;
import io.github.crowdfund.domain.user.UserRepository;
import io.github.crowdfund.feature.auth.dto.login.LoginRequest;
import io.github.crowdfund.feature.auth.dto.login.LoginResponse;
import io.github.crowdfund.feature.auth.dto.login.UserProfileInfo;
import io.github.crowdfund.feature.auth.dto.signup.SignUpRequest;
import io.github.crowdfund.global.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 로그인 로직
     */
    public LoginResponse login(LoginRequest request) {
        // 1. Email/Password 기반의 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());

        // 2. 실제로 검증 (사용자 비밀번호 체크)
        // authenticate 메서드가 실행될 때 SecurityUserService의 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        String accessToken = tokenProvider.createToken(authentication);

        // 4. 유저 정보 조회 (프론트엔드 프로필용)
        UserProfileInfo userInfo = userRepository.findByEmail(request.email())
                .map(user -> new UserProfileInfo(
                        user.email(),
                        user.nickname()
                ))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return new LoginResponse(accessToken, userInfo);
    }

    /**
     * 회원가입 로직
     */
    @Transactional
    public void signup(SignUpRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 비밀번호 암호화 및 유저 저장
        User user = new User(
                null,
                request.email(),
                passwordEncoder.encode(request.password()),
                request.nickname(),
                request.name(),
                request.phone(),
                "USER", // 기본 권한
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );

        userRepository.save(user);
    }
}
