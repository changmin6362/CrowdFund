package io.github.crowdfund.global.security;

import io.github.crowdfund.global.security.handler.JwtAccessDeniedHandler;
import io.github.crowdfund.global.security.handler.JwtAuthenticationEntryPoint;
import io.github.crowdfund.global.security.jwt.JwtAuthenticationFilter;
import io.github.crowdfund.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // REST API이므로 CSRF 보안 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // Form Login 및 HTTP Basic 인증 비활성화 (JWT 사용 전제로 기본 설정)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 세션을 사용하지 않도록 설정 (Stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // JWT 토큰 관련 예외 처리 핸들러 등록
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                // 요청에 대한 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // Swagger UI 및 API 문서 관련 경로는 모두 허용
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // 인증 및 회원가입 관련 경로 허용
                        .requestMatchers("/api/auth/**").permitAll()
                        // 공개 조회 API 허용 (프로젝트, 카테고리, 리워드, 댓글 등)
                        .requestMatchers(HttpMethod.GET, "/api/projects/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/projects/*/rewards").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/projects/*/comments").permitAll()
                        // 관리자 전용 경로 설정
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // 그 외 모든 /api/** 경로는 인증이 필요하도록 설정
                        .requestMatchers("/api/**").authenticated()
                        // 그 외 모든 요청은 허용 (정적 리소스 등)
                        .anyRequest().permitAll()
                )
                // JWT 필터 추가
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
