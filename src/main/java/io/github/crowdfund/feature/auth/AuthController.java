package io.github.crowdfund.feature.auth;

import io.github.crowdfund.feature.auth.dto.login.LoginRequest;
import io.github.crowdfund.feature.auth.dto.login.LoginResponse;
import io.github.crowdfund.feature.auth.dto.signup.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService service;

    /**
     * 회원가입 페이지 이동
     */
    @GetMapping("/signup")
    public String signUpPage(@ModelAttribute("signUpRequest") SignUpRequest request) {
        return "auth/signup";
    }

    /**
     * 회원가입 처리
     *
     * @param request 회원가입 요청 정보
     * @return redirect to login page
     */
    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("signUpRequest") SignUpRequest request,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }
        service.signup(request);
        return "redirect:/auth/login";
    }

    /**
     * 로그인 페이지 이동
     */
    @GetMapping("/login")
    public String loginPage(@ModelAttribute("loginRequest") LoginRequest request) {
        return "auth/login";
    }

    /**
     * 로그인 처리
     *
     * @param request 로그인 요청 정보
     * @return redirect to home or back to login with error
     */
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginRequest") LoginRequest request,
                        BindingResult bindingResult,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }
        try {
            LoginResponse response = service.login(request);
            // TODO: JWT 토큰을 쿠키에 저장하는 로직이 필요
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "로그인에 실패했습니다: " + e.getMessage());
            return "auth/login";
        }
    }
}
