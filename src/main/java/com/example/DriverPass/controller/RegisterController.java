package com.example.DriverPass.controller;

import com.example.DriverPass.model.Role;
import com.example.DriverPass.model.User;
import com.example.DriverPass.security.CustomUserDetailsService;
import com.example.DriverPass.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final CustomUserDetailsService userDetailsService;

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

        if (userService.getByEmail(user.getEmail()).isPresent()) {
            result.rejectValue("email", "error.user", "Email already registered.");
        }

        if (result.hasErrors()) {
            return "register";
        }

        user.setRole(Role.ROLE_STUDENT);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        // Auto-login after registration
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        session.setAttribute("user", user);

        return "redirect:/student/home";
    }
}
