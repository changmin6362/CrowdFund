package io.github.authservice.crowdfund.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.authservice.crowdfund.global.common.ApiResult;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

public class TestUtils {
    public static <T> ApiResult<T> convertToApiResult(MvcResult result, ObjectMapper objectMapper, TypeReference<ApiResult<T>> typeReference) throws Exception {
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(content, typeReference);
    }
}
