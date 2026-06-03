package io.github.crowdfund.global.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Spring Security가 관리하는 User 클래스를 확장하여 userId를 포함하도록 확장한 클래스
 */
@Getter
public class SecurityUser extends User {

    private final Long userId;

    public SecurityUser(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public static SecurityUser from(io.github.crowdfund.domain.user.User user, Collection<? extends GrantedAuthority> authorities) {
        return new SecurityUser(
                user.id(),
                user.email(),
                user.password(),
                authorities
        );
    }
}
