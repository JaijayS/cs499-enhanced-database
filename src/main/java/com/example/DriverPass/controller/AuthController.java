package com.example.DriverPass.controller;

import com.example.DriverPass.model.User;
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

    // Handles the login page display
    @GetMapping("/")
    public String showLogin() {
        return "login";
    }

    // Handles the registration page display
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    // Redirects user to their respective home page based on role after successful login
    @GetMapping("/default")
    public String redirectAfterLogin(Authentication auth, HttpSession session) {
        if (auth != null && auth.getAuthorities() != null) {
            String email = auth.getName();
            User user = userService.findByEmail(email);
            session.setAttribute("user", user); //

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
