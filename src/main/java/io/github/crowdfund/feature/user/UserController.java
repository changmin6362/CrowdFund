package io.github.crowdfund.feature.user;

import io.github.crowdfund.feature.user.dto.fetch.UserDataInfo;
import io.github.crowdfund.feature.user.dto.update.UserUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/me")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService service;

    /**
     * 내 정보 조회 (Thymeleaf)
     *
     * @param userDetails 인증된 사용자 정보
     * @return 뷰 이름
     */
    @GetMapping
    public String me(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserDataInfo user = service.fetchByEmail(userDetails.getUsername()).user();
        model.addAttribute("user", user);
        model.addAttribute("updateRequest", new UserUpdateRequest(user.nickname(), user.name(), user.phone()));
        return "user/me";
    }

    /**
     * 내 정보 수정
     *
     * @param userDetails 인증된 사용자 정보
     * @param request     수정할 데이터
     * @return redirect to me
     */
    @PostMapping("/update")
    public String update(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @ModelAttribute("updateRequest") UserUpdateRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", service.fetchByEmail(userDetails.getUsername()).user());
            return "user/me";
        }
        service.updateByEmail(userDetails.getUsername(), request);
        return "redirect:/users/me?success";
    }

    /**
     * 회원 탈퇴
     *
     * @param userDetails 인증된 사용자 정보
     * @return redirect to logout
     */
    @PostMapping("/delete")
    public String delete(@AuthenticationPrincipal UserDetails userDetails) {
        service.deleteByEmail(userDetails.getUsername());
        return "redirect:/auth/logout";
    }
}
