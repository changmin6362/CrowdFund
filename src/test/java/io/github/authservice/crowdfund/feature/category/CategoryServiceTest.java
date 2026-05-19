package io.github.authservice.crowdfund.feature.category;

import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

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

        mockMvc.perform(post("/api/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("카테고리가 생성되었습니다."))
                .andExpect(jsonPath("$.category.name").value("새 카테고리"))
                .andExpect(jsonPath("$.category.parentId").value(savedRootCategory.id()))
                .andDo(print());
    }

    @Test
    void 카테고리_트리_조회_테스트() throws Exception {
        mockMvc.perform(get("/api/categories/tree"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("카테고리 조회가 완료되었습니다."))
                .andExpect(jsonPath("$.categoryTree").isArray())
                .andExpect(jsonPath("$.categoryTree[?(@.name == 'Root Category')]").exists())
                .andExpect(jsonPath("$.categoryTree[?(@.name == 'Root Category')].children[?(@.name == 'Child Category')]").exists())
                .andDo(print());
    }

    @Test
    void 카테고리_이름_수정_테스트() throws Exception {
        String patchRequest = """
                {
                    "name": "수정된 카테고리 이름"
                }
                """;

        mockMvc.perform(patch("/api/admin/categories/{categoryId}/name", savedRootCategory.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("카테고리 이름이 수정되었습니다."))
                .andDo(print());
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

        mockMvc.perform(patch("/api/admin/categories/{categoryId}/parent", savedChildCategory.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("카테고리 부모가 변경되었습니다."))
                .andDo(print());
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

        mockMvc.perform(patch("/api/admin/categories/sort-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("카테고리 순서가 변경되었습니다."))
                .andDo(print());
    }

    @Test
    void 카테고리_삭제_테스트() throws Exception {
        mockMvc.perform(delete("/api/admin/categories/{categoryId}", savedChildCategory.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("카테고리가 삭제되었습니다."))
                .andDo(print());
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
