package io.github.crowdfund.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 공통 응답 구조
 *
 * @param <T> 응답 데이터 타입
 */
public record ApiResult<T>(
        @Schema(description = "응답 메시지", example = "이건 응답 메시지 샘플입니다.")
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T data
) {
    public static <T> ApiResult<T> success(String message, T data) {
        return new ApiResult<>(message, data);
    }

    public static <T> ApiResult<T> success(String message) {
        return new ApiResult<>(message, null);
    }

    public static <T> ApiResult<T> error(String message) {
        return new ApiResult<>(message, null);
    }
}
