package com.example.DriverPass.security;

import com.example.DriverPass.model.User;
import com.example.DriverPass.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepo;

    // This class handles the logic after a successful login
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName(); // email = username
        Optional<User> optionalUser = userRepo.findByEmail(email);

        // If user is found, set it in the session and redirect based on role
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            request.getSession().setAttribute("user", user);

            // Redirect based on user role
            String role = user.getRole().name();
            if ("ROLE_ADMIN".equals(role)) {
                response.sendRedirect("/admin/home");
            } else if ("ROLE_TEACHER".equals(role)) {
                response.sendRedirect("/teacher/home");
            } else if ("ROLE_STUDENT".equals(role)) {
                response.sendRedirect("/student/home");
            } else {
                response.sendRedirect("/");
            }
        } else {
            response.sendRedirect("/?error=nouser");
        }
    }
}
