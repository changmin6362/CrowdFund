package io.github.authservice.crowdfund.feature.category.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryRepository;
import io.github.authservice.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
import io.github.authservice.crowdfund.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserCategoryControllerTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        Category savedRootCategory = categoryRepository.save(new Category(
                null, null, "Root Category", 1, 10, true
        ));

        Category savedChildCategory = categoryRepository.save(new Category(
                null, savedRootCategory.id(), "Child Category", 2, 10, true
        ));
    }

    @Test
    void 카테고리_트리_조회_테스트() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<UserFetchCategoryResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("카테고리 트리 조회에 성공했습니다.");
        assertThat(apiResult.data().categoryTree()).isNotEmpty();
        assertThat(apiResult.data().categoryTree()).anyMatch(node -> node.name().equals("Root Category"));
        assertThat(apiResult.data().categoryTree().stream()
                .filter(node -> node.name().equals("Root Category"))
                .flatMap(node -> node.children().stream())
                .anyMatch(child -> child.name().equals("Child Category")))
                .isTrue();
    }
}
