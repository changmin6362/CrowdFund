package io.github.authservice.crowdfund.feature.category;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryRepository;
import io.github.authservice.crowdfund.feature.category.admin.dto.create.AdminCreateCategoryResponse;
import io.github.authservice.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
import io.github.authservice.crowdfund.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Category savedRootCategory;
    private Category savedChildCategory;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        savedRootCategory = categoryRepository.save(new Category(
                null, null, "Root Category", 1, 10, true
        ));

        savedChildCategory = categoryRepository.save(new Category(
                null, savedRootCategory.id(), "Child Category", 2, 10, true
        ));
    }

    @Test
    void 카테고리_생성_테스트() throws Exception {
        String createRequest = """
                {
                    "name": "새 카테고리",
                    "parentId": %d
                }
                """.formatted(savedRootCategory.id());

        MvcResult result = mockMvc.perform(post("/api/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        ApiResult<AdminCreateCategoryResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("카테고리 생성에 성공했습니다.");
        assertThat(apiResult.data().category().name()).isEqualTo("새 카테고리");
        assertThat(apiResult.data().category().parentId()).isEqualTo(savedRootCategory.id());
    }

    @Test
    void 카테고리_트리_조회_테스트() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/categories/tree"))
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

    @Test
    void 카테고리_이름_수정_테스트() throws Exception {
        String patchRequest = """
                {
                    "name": "수정된 카테고리 이름"
                }
                """;

        MvcResult result = mockMvc.perform(patch("/api/admin/categories/{categoryId}/name", savedRootCategory.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<AdminCreateCategoryResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("카테고리 이름 변경에 성공했습니다.");
    }

    @Test
    void 카테고리_부모_변경_테스트() throws Exception {
        Category anotherRoot = categoryRepository.save(new Category(
                null, null, "Another Root", 1, 20, true
        ));

        String patchRequest = """
                {
                    "parentId": %d
                }
                """.formatted(anotherRoot.id());

        MvcResult result = mockMvc.perform(patch("/api/admin/categories/{categoryId}/parent", savedChildCategory.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<AdminCreateCategoryResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("카테고리 부모 변경에 성공했습니다.");
    }

    @Test
    void 카테고리_순서_변경_테스트() throws Exception {
        String patchRequest = """
                {
                    "categories": [
                        {
                            "id": %d,
                            "sortOrder": 5
                        },
                        {
                            "id": %d,
                            "sortOrder": 15
                        }
                    ]
                }
                """.formatted(savedRootCategory.id(), savedChildCategory.id());

        MvcResult result = mockMvc.perform(patch("/api/admin/categories/sort-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<AdminCreateCategoryResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("카테고리 정렬 순서 변경에 성공했습니다.");
    }

    @Test
    void 카테고리_삭제_테스트() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/admin/categories/{categoryId}", savedChildCategory.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<AdminCreateCategoryResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("카테고리 삭제에 성공했습니다.");
    }

    @Test
    void 카테고리_조회_실패_테스트() throws Exception {
        mockMvc.perform(patch("/api/admin/categories/9999/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"fail\"}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
