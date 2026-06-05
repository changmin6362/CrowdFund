package io.github.crowdfund.feature.comment.project;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.crowdfund.domain.category.Category;
import io.github.crowdfund.domain.category.CategoryRepository;
import io.github.crowdfund.domain.comment.Comment;
import io.github.crowdfund.domain.comment.CommentRepository;
import io.github.crowdfund.domain.project.Project;
import io.github.crowdfund.domain.project.ProjectRepository;
import io.github.crowdfund.domain.project.ProjectStatus;
import io.github.crowdfund.domain.user.User;
import io.github.crowdfund.domain.user.UserRepository;
import io.github.crowdfund.feature.comment.my.dto.delete.ProjectCommentDeleteResponse;
import io.github.crowdfund.feature.comment.my.dto.update.ProjectCommentUpdateResponse;
import io.github.crowdfund.feature.comment.project.dto.create.ProjectCommentCreateResponse;
import io.github.crowdfund.feature.comment.project.dto.fetch.ProjectCommentsFetchResponse;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProjectCommentControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User savedUser;
    private Project savedProject;

    @BeforeEach
    void setup(TestInfo testInfo) {
        System.out.println("\n>>> 실행테스트: " + testInfo.getTestMethod().get().getName());

        savedUser = userRepository.save(new User(
                null, "test@test.com", "pass", "tester", "홍길동", "010-1234-5678", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        Category savedCategory = categoryRepository.save(new Category(
                null, null, "테스트 카테고리", 1, 1, true
        ));

        savedProject = projectRepository.save(new Project(
                null, savedCategory.id(), savedUser.id(), "테스트 프로젝트", "[]",
                new BigDecimal("1000000"), BigDecimal.ZERO, LocalDateTime.now().plusDays(30),
                ProjectStatus.ONGOING, LocalDateTime.now()
        ));
    }

    @Test
    void 댓글_작성_테스트() throws Exception {
        String createRequest = """
                {
                    "content": "테스트 댓글입니다."
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/projects/{projectId}/comments/{userId}", savedProject.id(), savedUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        ApiResult<ProjectCommentCreateResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("댓글 작성에 성공했습니다.");
        assertThat(apiResult.data().createdComment().writerName()).isEqualTo(savedUser.nickname());
        assertThat(apiResult.data().createdComment().content()).isEqualTo("테스트 댓글입니다.");
    }

    @Test
    void 댓글_수정_테스트() throws Exception {
        Comment savedComment = commentRepository.save(new Comment(
                null, savedUser.id(), savedProject.id(), "기존 댓글", LocalDateTime.now()
        ));

        String patchRequest = """
                {
                    "content": "수정된 댓글입니다."
                }
                """;

        MvcResult result = mockMvc.perform(patch("/api/comments/{commentId}/{userId}", savedComment.id(), savedUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<ProjectCommentUpdateResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("댓글 수정에 성공했습니다.");
        assertThat(apiResult.data().patchedComment().content()).isEqualTo("수정된 댓글입니다.");
    }

    @Test
    void 프로젝트_댓글_목록_조회_정렬_및_페이지네이션_테스트() throws Exception {
        // 15개의 댓글 생성
        for (int i = 1; i <= 15; i++) {
            commentRepository.save(new Comment(
                    null, savedUser.id(), savedProject.id(), "댓글" + i, LocalDateTime.now().plusSeconds(i)
            ));
        }

        // 첫 번째 페이지 조회 (limit=10)
        MvcResult firstResult = mockMvc.perform(get("/api/projects/{projectId}/comments", savedProject.id())
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andReturn();

        ApiResult<ProjectCommentsFetchResponse> firstPage = TestUtils.convertToApiResult(firstResult, objectMapper, new TypeReference<>() {});
        assertThat(firstPage.data().comments()).hasSize(10);
        assertThat(firstPage.data().hasNext()).isTrue();
        assertThat(firstPage.data().nextCursor()).isNotNull();

        // 두 번째 페이지 조회 (커서 사용)
        MvcResult secondResult = mockMvc.perform(get("/api/projects/{projectId}/comments", savedProject.id())
                        .param("limit", "10")
                        .param("createdAt", firstPage.data().nextCursor().createdAt().toString())
                        .param("id", firstPage.data().nextCursor().id().toString()))
                .andExpect(status().isOk())
                .andReturn();

        ApiResult<ProjectCommentsFetchResponse> secondPage = TestUtils.convertToApiResult(secondResult, objectMapper, new TypeReference<>() {});
        assertThat(secondPage.data().comments()).hasSize(5);
        assertThat(secondPage.data().hasNext()).isFalse();
        assertThat(secondPage.data().nextCursor()).isNull();
    }

    @Test
    void 내_댓글_삭제_테스트() throws Exception {
        Comment savedComment = commentRepository.save(new Comment(
                null, savedUser.id(), savedProject.id(), "삭제할 댓글", LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(delete("/api/comments/{commentId}/{userId}", savedComment.id(), savedUser.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ApiResult<ProjectCommentDeleteResponse> apiResult = TestUtils.convertToApiResult(result, objectMapper, new TypeReference<>() {});

        assertThat(apiResult.message()).isEqualTo("내 댓글 삭제에 성공했습니다.");
        assertThat(apiResult.data().deletedCommentId()).isEqualTo(savedComment.id());
    }

    @Test
    void 내_댓글_삭제_실패_테스트_권한없음() throws Exception {
        User otherUser = userRepository.save(new User(
                null, "other@test.com", "pass", "other", "other", "010-0000-0000", "USER", LocalDateTime.now(), LocalDateTime.now(), null
        ));

        Comment savedComment = commentRepository.save(new Comment(
                null, savedUser.id(), savedProject.id(), "남의 댓글", LocalDateTime.now()
        ));

        mockMvc.perform(delete("/api/comments/{commentId}/{userId}", savedComment.id(), otherUser.id()))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void 댓글_수정_실패_테스트_존재하지않음() throws Exception {
        mockMvc.perform(patch("/api/comments/9999/{userId}", savedUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "content": "실패 테스트"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
