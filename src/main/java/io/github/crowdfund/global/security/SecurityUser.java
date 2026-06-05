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
    private final String nickname;
    private final java.time.LocalDateTime deletedAt;

    public SecurityUser(Long userId, String username, String password, String nickname, java.time.LocalDateTime deletedAt, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.nickname = nickname;
        this.deletedAt = deletedAt;
    }

    public static SecurityUser from(io.github.crowdfund.domain.user.User user, Collection<? extends GrantedAuthority> authorities) {
        return new SecurityUser(
                user.id(),
                user.email(),
                user.password(),
                user.nickname(),
                user.deletedAt(),
                authorities
        );
    }

    /**
     * 현재 사용자가 소유자가 맞는지 확인 하는 메서드
     */
    public boolean isOwner(Long targetUserId) {
        return this.userId == null || !this.userId.equals(targetUserId);
    }
}
