package io.github.crowdfund.global.config.security;

import io.github.crowdfund.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 스프링 시큐리티의 인증 프로세스에서 데이터베이스에서 사용자 정보를 조회하는 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 스프링 시큐리티 규격에 맞춰 이메일을 고유 식별자로 사용하여 사용자 및 권한 정보를 조회하는 메서드
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> User.builder()
                        .username(user.email()) // 시큐리티 내부 고유 키값 자리에 이메일 매핑
                        .password(user.password()) // 암호화된 패스워드 매핑
                        .roles(user.role())  // DB에 정의된 'USER', 'ADMIN' 형태의 권한 매핑
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));
    }
}
