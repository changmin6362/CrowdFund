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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                // CORS 설정 적용
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // REST API이므로 CSRF 보안 비활성화 (Thymeleaf 도입 후에도 쿠키 방식 JWT를 사용하므로 유지 혹은 설정 조정)
                .csrf(AbstractHttpConfigurer::disable)
                // Form Login 활성화 (SSR에서 로그인 요청을 처리하기 위해 필요함)
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        // 위에 작성된 경로로 보내진 로그인 요청이 UsernamePasswordAuthenticationFilter에 의해 자동으로 가로채지는 것을 막기 위해 아래에서 가로채야하는 가짜 경로를 명시함
                        .loginProcessingUrl("/auth/login/process") // 기본 login 처리 URL과 겹치지 않게 분리 (실제 처리는 AuthController에서 함)
                        .permitAll()
                )
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
                        .requestMatchers("/auth/**").permitAll()
                        // 공개 조회 API 허용 (프로젝트, 카테고리, 리워드, 댓글 등)
                        .requestMatchers(HttpMethod.GET, "/projects/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/projects/*/rewards").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/projects/*/comments").permitAll()
                        // 관리자 전용 경로 설정
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // 내 후원 관련 경로 설정
                        .requestMatchers("/pledges/me/**").authenticated()
                        // 창작자 후원 관련 경로 설정
                        .requestMatchers("/creator/pledges/**").authenticated()
                        // 창작자 프로젝트 관련 경로 설정
                        .requestMatchers("/creator/projects/**").authenticated()
                        // 그 외 모든 /api/** 경로는 인증이 필요하도록 설정
                        .requestMatchers("/api/**").authenticated()
                        // 그 외 모든 요청은 허용 (정적 리소스 등)
                        .anyRequest().permitAll()
                )
                // addFilterBefore(A, B)를 사용해서 서버에 요청이 들어왔을 때 B보다 A가 먼저 동작하게 함 (UsernamePasswordAuthenticationFilter: 기본 로그인 처리 필터)
                // 1. 브라우저가 요청을 보내면 JwtAuthenticationFilter가 동작함. 이 필터는 쿠키에서 JWT 토큰을 추출해서 서버 메모리에 저장함
                // 2. 브라우저에서 formLogin( form -> form.loginPage()에 정의된 경로로 보내진 로그인 요청이 UsernamePasswordAuthenticationFilter에 의해 자동으로 가로채져서 JwtAuthenticationFilter로 전달됨
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:3000", // 로컬 개발 환경 포트 허용
                "https://crowd-fund-front*.vercel.app"  // crowd-fund-front로 시작하는 모든 vercel 주소 허용
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
