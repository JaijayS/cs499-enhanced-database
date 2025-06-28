package com.example.DriverPass.controller;

import com.example.DriverPass.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @GetMapping("/")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/default")
    public String redirectAfterLogin(Authentication auth) {
        if (auth != null && auth.getAuthorities() != null) {
            for (GrantedAuthority authority : auth.getAuthorities()) {
                String role = authority.getAuthority();
                switch (role) {
                    case "ROLE_ADMIN":
                        return "redirect:/admin/home";
                    case "ROLE_TEACHER":
                        return "redirect:/teacher/home";
                    case "ROLE_STUDENT":
                        return "redirect:/student/home";
                }
            }
        }
        return "redirect:/";
    }
}
