package io.github.crowdfund.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.crowdfund.global.common.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 인증되지 않은 사용자가 보호된 리소스에 접근하려 할 때 호출되는 엔트리 포인트
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String message = "인증이 필요한 서비스입니다. 로그인 후 이용해주세요.";
        
        ApiResult<Void> apiResult = ApiResult.error(message);
        response.getWriter().write(objectMapper.writeValueAsString(apiResult));
    }
}
