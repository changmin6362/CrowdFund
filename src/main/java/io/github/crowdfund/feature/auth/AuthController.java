package io.github.crowdfund.feature.auth;

import io.github.crowdfund.feature.auth.dto.login.LoginRequest;
import io.github.crowdfund.feature.auth.dto.login.LoginResponse;
import io.github.crowdfund.feature.auth.dto.signup.SignUpRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${cookie.secure}")
    private boolean cookieSecure;

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
                        HttpServletResponse response,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }
        try {
            LoginResponse loginResponse = service.login(request);
            
            // JWT 토큰을 쿠키에 저장
            Cookie cookie = new Cookie("jwt", loginResponse.accessToken());
            cookie.setHttpOnly(true);
            cookie.setSecure(cookieSecure);
            cookie.setPath("/");
            cookie.setMaxAge(1800); // 30분
            response.addCookie(cookie);

            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "로그인에 실패했습니다: " + e.getMessage());
            return "auth/login";
        }
    }
    /**
     * 로그아웃 처리
     */
    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
