package io.github.crowdfund.global.security.jwt;

import io.github.crowdfund.global.security.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    
    private final SecretKey key;
    private final long tokenValidityInMilliseconds;

    /**
     * JWT 토큰 생성자
     */
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long tokenValidityInMilliseconds) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
    }

    /**
     * Access Token 생성
     *
     * @param authentication 인증 정보 (사용자 식별자 및 권한 포함)
     * @return 생성된 JWT Access Token
     */
    public String createToken(Authentication authentication) {
        return createToken(authentication, this.tokenValidityInMilliseconds);
    }

    /**
     * 실제 JWT 토큰 생성 로직
     * accessToken에는 다음과 같은 정보가 포함됩니다:
     * 1. Subject: 사용자의 식별자 (email)
     * 2. Claim ("auth"): 사용자의 권한 정보 (예: ROLE_USER, ROLE_ADMIN)
     * 3. Expiration: 토큰의 만료 일시
     * 4. Signature: 서버의 Secret Key로 서명되어 위변조를 방지함
     *
     * @param authentication         인증 정보
     * @param validityInMilliseconds 토큰 유효 시간 (밀리초)
     * @return 암호화된 JWT 토큰 문자열
     */
    private String createToken(Authentication authentication, long validityInMilliseconds) {
        // SecurityUser에서 userId, nickname 추출
        Long userId = null;
        String nickname = null;
        if (authentication.getPrincipal() instanceof SecurityUser userDetails) {
            userId = userDetails.getUserId();
            nickname = userDetails.getNickname();
        }

        // 사용자의 권한 목록을 콤마(,)로 구분된 문자열로 변환 (예: "ROLE_USER,ROLE_ADMIN")
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + validityInMilliseconds); // 현재 시간 + 설정된 유효 시간

        return Jwts.builder()
                .subject(authentication.getName()) // 토큰 제목: 사용자 이메일 설정
                .claim("userId", userId)           // 커스텀 클레임: 사용자 ID 추가
                .claim("nickname", nickname)       // 커스텀 클레임: 닉네임 추가
                .claim("auth", authorities)        // 커스텀 클레임: 권한 정보 추가
                .signWith(key)                     // 서명: 설정된 Secret Key와 알고리즘 사용
                .expiration(validity)              // 만료 일시 설정
                .compact();                        // 최종적으로 직렬화하여 토큰 생성
    }

    // 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        Long userId = claims.get("userId", Long.class);
        String nickname = claims.get("nickname", String.class);
        SecurityUser principal = new SecurityUser(userId, claims.getSubject(), "", nickname, null, authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // 로깅 생략 (필요 시 추가)
        }
        return false;
    }
}
