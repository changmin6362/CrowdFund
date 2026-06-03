package io.github.crowdfund.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.crowdfund.global.common.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 인증은 되었으나 리소스에 대한 접근 권한이 없는 경우 호출되는 핸들러
 */
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        String message = "해당 리소스에 접근할 권한이 없습니다.";

        ApiResult<Void> apiResult = ApiResult.error(message);
        response.getWriter().write(objectMapper.writeValueAsString(apiResult));
    }
}
