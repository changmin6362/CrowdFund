package io.github.crowdfund.feature.auth;

import io.github.crowdfund.domain.user.User;
import io.github.crowdfund.domain.user.UserRepository;
import io.github.crowdfund.feature.auth.dto.login.LoginRequest;
import io.github.crowdfund.feature.auth.dto.login.LoginResponse;
import io.github.crowdfund.feature.auth.dto.login.UserProfileInfo;
import io.github.crowdfund.feature.auth.dto.signup.SignUpRequest;
import io.github.crowdfund.global.security.SecurityUser;
import io.github.crowdfund.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
        // 1. Spring Security에 전달할 인증 요청 객체(이메일, 비밀번호) 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());

        // 2. Spring Security에게 전달받을 인증 결과 객체 생성
        Authentication authentication;
        try {
            // authenticate 메서드로 SecurityUserService의 loadUserByUsername 메서드 호출해서 Spring Security가 사용자 인증을 수행
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (DisabledException e) {
            throw new IllegalArgumentException("탈퇴한 사용자입니다.");
        } catch (InternalAuthenticationServiceException e) {
            if (e.getCause() instanceof DisabledException) {
                throw new IllegalArgumentException("탈퇴한 사용자입니다.");
            }
            throw new IllegalArgumentException("이메일이나 비밀번호가 잘못되었습니다.");
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("이메일이나 비밀번호가 잘못되었습니다.");
        }

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        String accessToken = tokenProvider.createToken(authentication);

        // 5. 유저 정보 구성 (프론트엔드 프로필용)
        UserProfileInfo userInfo = new UserProfileInfo(
                securityUser.getUsername(), // email
                securityUser.getNickname() // nickname
        );

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
