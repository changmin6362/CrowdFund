package io.github.crowdfund.global.security;

import io.github.crowdfund.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;
import java.util.List;

/**
 * 스프링 시큐리티의 인증 프로세스에서 데이터베이스에서 사용자 정보를 조회하는 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 스프링 시큐리티 규격에 맞춰 이메일을 고유 식별자로 사용하여 사용자 및 권한 정보를 조회하는 메서드
     * AuthenticationManagerBuilder의 .getObject().authenticate가 호출될 때 실행된다.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> {
                    List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                            new SimpleGrantedAuthority("ROLE_" + user.role())
                    );
                    return SecurityUser.from(user, authorities);
                })
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));
    }
}
