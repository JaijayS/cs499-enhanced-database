package com.example.DriverPass.controller;

import com.example.DriverPass.model.Role;
import com.example.DriverPass.model.User;
import com.example.DriverPass.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user,
                               BindingResult result,
                               HttpSession session,
                               Model model) {

        // Check for existing email
        if (userService.getByEmail(user.getEmail()).isPresent()) {
            result.rejectValue("email", "error.user", "Email already registered.");
        }

        if (result.hasErrors()) {
            return "register";
        }

        // Set role and encode password
        user.setRole(Role.ROLE_STUDENT);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        session.setAttribute("user", user);
        return "redirect:/student/home"; // redirect to student dashboard
    }
}
