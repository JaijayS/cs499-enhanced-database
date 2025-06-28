// src/main/java/com/example/DriverPass/controller/AdminController.java
package com.example.DriverPass.controller;

import com.example.DriverPass.model.User;
import com.example.DriverPass.repository.CourseRepository;
import com.example.DriverPass.repository.UserRepository;
import com.example.DriverPass.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepo;
    private final CourseRepository courseRepo;
    private final VehicleRepository vehicleRepo;

    @GetMapping("/home")
    public String adminDashboard(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        if (currentUser != null) {
            User admin = userRepo.findByEmail(currentUser.getUsername()).orElse(null);
            model.addAttribute("admin", admin);
        }

        model.addAttribute("allUsers", userRepo.findAll());
        model.addAttribute("allCourses", courseRepo.findAll());
        model.addAttribute("allVehicles", vehicleRepo.findAll());

        return "admin/home";
    }
}
