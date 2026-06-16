package io.github.crowdfund.feature.project.user;

import io.github.crowdfund.domain.project.ProjectStatus;
import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
@Validated
public class UserProjectController {

    private final UserProjectService service;

    /**
     * 프로젝트 목록 조회 (복합 커서 기반 최신순 페이지네이션)
     *
     * @param statuses   프로젝트 상태 필터링
     * @param categoryId 카테고리 ID 필터링
     */
    @GetMapping()
    public String fetch(
            @RequestParam(required = false) List<ProjectStatus> statuses,
            @RequestParam(required = false) @Positive(message = "카테고리 ID는 양수여야 합니다.") Integer categoryId,
            @ModelAttribute CursorRequest cursorRequest,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            Model model
    ) {
        model.addAttribute("projectData", service.fetch(statuses, categoryId, cursorRequest, limit));
        model.addAttribute("statuses", statuses);
        model.addAttribute("categoryId", categoryId);
        return "project/list";
    }

    /**
     * 프로젝트 상세 조회
     *
     * @param projectId 프로젝트 ID
     */
    @GetMapping("/{projectId}")
    public String detail(@PathVariable Long projectId, Model model) {
        model.addAttribute("project", service.detail(projectId).projectDetail());
        return "project/detail";
    }
}