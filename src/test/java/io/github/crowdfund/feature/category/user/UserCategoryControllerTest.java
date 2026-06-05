package io.github.crowdfund.feature.category.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.crowdfund.domain.category.Category;
import io.github.crowdfund.domain.category.CategoryRepository;
import io.github.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.github.crowdfund.utils.TestUtils;
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

        // 루트 카테고리 2개 생성 (순서 20, 10 순서로 저장하여 정렬 확인)
        categoryRepository.save(new Category(null, null, "Root 2", 0, 20, true));
        Category savedRoot1 = categoryRepository.save(new Category(null, null, "Root 1", 0, 10, true));

        // Root 1 아래 자식 2개 생성 (순서 200, 100 순서로 저장)
        categoryRepository.save(new Category(null, savedRoot1.id(), "Child 2", 1, 200, true));
        categoryRepository.save(new Category(null, savedRoot1.id(), "Child 1", 1, 100, true));
    }

    @Test
    void 카테고리_트리_조회_정렬_테스트() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<UserFetchCategoryResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("카테고리 트리 조회에 성공했습니다.");
        
        var tree = apiResult.data().categoryTree();
        assertThat(tree).hasSizeGreaterThanOrEqualTo(2);

        // Root 노드 정렬 확인 (Root 1: 10, Root 2: 20)
        var root1 = tree.stream().filter(n -> n.name().equals("Root 1")).findFirst().orElseThrow();
        var root2 = tree.stream().filter(n -> n.name().equals("Root 2")).findFirst().orElseThrow();
        
        int root1Index = tree.indexOf(root1);
        int root2Index = tree.indexOf(root2);
        assertThat(root1Index).isLessThan(root2Index);

        // 자식 노드 정렬 확인 (Child 1: 100, Child 2: 200)
        var children = root1.children();
        assertThat(children).hasSize(2);
        assertThat(children.get(0).name()).isEqualTo("Child 1");
        assertThat(children.get(1).name()).isEqualTo("Child 2");
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
        assertThat(apiResult.data().categoryTree()).anyMatch(node -> node.name().equals("Root 1"));
        assertThat(apiResult.data().categoryTree().stream()
                .filter(node -> node.name().equals("Root 1"))
                .flatMap(node -> node.children().stream())
                .anyMatch(child -> child.name().equals("Child 1")))
                .isTrue();
    }
}
