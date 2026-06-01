package io.github.crowdfund.feature.category.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.crowdfund.domain.category.Category;
import io.github.crowdfund.domain.category.CategoryRepository;
import io.github.crowdfund.feature.category.admin.dto.create.AdminCategoryCreateResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.github.crowdfund.utils.TestUtils;
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
class AdminCategoryControllerTest {

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
                null, null, "Root Category", 0, 10, true
        ));

        savedChildCategory = categoryRepository.save(new Category(
                null, savedRootCategory.id(), "Child Category", 1, 10, true
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

        ApiResult<AdminCategoryCreateResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("카테고리 생성에 성공했습니다.");
        assertThat(apiResult.data().category().name()).isEqualTo("새 카테고리");
        assertThat(apiResult.data().category().parentId()).isEqualTo(savedRootCategory.id());
        assertThat(apiResult.data().category().depth()).isEqualTo(1);
        assertThat(apiResult.data().category().isActive()).isTrue();
    }

    @Test
    void 카테고리_이름_수정_테스트() throws Exception {
        String patchRequest = """
                {
                    "name": "수정된 카테고리 이름"
                }
                """;

        mockMvc.perform(patch("/api/admin/categories/{categoryId}/rename", savedRootCategory.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 카테고리_부모_변경_테스트() throws Exception {
        // Given
        Category anotherRoot = categoryRepository.save(new Category(
                null, null, "Another Root", 0, 50, true
        ));

        String patchRequest = """
                {
                    "parentId": %d
                }
                """.formatted(anotherRoot.id());

        // When
        mockMvc.perform(patch("/api/admin/categories/{categoryId}/parent", savedChildCategory.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andDo(print());

        // Then
        Category updated = categoryRepository.findById(savedChildCategory.id()).orElseThrow();
        assertThat(updated.parentId()).isEqualTo(anotherRoot.id());
        assertThat(updated.depth()).isEqualTo(1);
        // 새로운 부모(anotherRoot)의 형제가 없으므로 기본값 10 예상 (기존 50은 root 레벨의 다른 카테고리이므로 무관)
        assertThat(updated.sortOrder()).isEqualTo(10);

        // 추가 검증: 자식이 있는 경우
        Category subChild = categoryRepository.save(new Category(
                null, savedChildCategory.id(), "Sub Child", 2, 10, true
        ));

        // When: savedChildCategory를 최상위로 다시 이동
        String moveRootRequest = """
                {
                    "parentId": null
                }
                """;
        mockMvc.perform(patch("/api/admin/categories/{categoryId}/parent", savedChildCategory.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveRootRequest))
                .andExpect(status().isOk());

        // Then: subChild의 depth도 같이 변경되어야 함 (2 -> 1)
        Category updatedSub = categoryRepository.findById(subChild.id()).orElseThrow();
        assertThat(updatedSub.depth()).isEqualTo(1);
        assertThat(updatedSub.sortOrder()).isEqualTo(10); // subChild의 sortOrder는 유지되어야 함
    }

    @Test
    void 카테고리_순서_변경_테스트() throws Exception {
        String patchRequest = """
                {
                    "categories": [
                        {
                            "categoryId": %d,
                            "sortOrder": 5
                        },
                        {
                            "categoryId": %d,
                            "sortOrder": 15
                        }
                    ]
                }
                """.formatted(savedRootCategory.id(), savedChildCategory.id());

        mockMvc.perform(patch("/api/admin/categories/sort-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 카테고리_삭제_테스트() throws Exception {
        mockMvc.perform(delete("/api/admin/categories/{categoryId}", savedChildCategory.id()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 카테고리_조회_실패_테스트() throws Exception {
        mockMvc.perform(patch("/api/admin/categories/9999/rename")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"fail\"}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
