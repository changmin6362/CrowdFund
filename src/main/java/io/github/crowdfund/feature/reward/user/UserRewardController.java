package io.github.crowdfund.feature.reward.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects/{projectId}/rewards")
@RequiredArgsConstructor
@Validated
public class UserRewardController {

    private final UserRewardService service;

    /**
     * 프로젝트의 리워드 목록 조회
     *
     * @param projectId 프로젝트 ID
     * @return 뷰 이름
     */
    @GetMapping
    public String fetch(@PathVariable Long projectId, Model model) {
        model.addAttribute("rewardData", service.fetch(projectId));
        model.addAttribute("projectId", projectId);
        return "reward/list";
    }
}

