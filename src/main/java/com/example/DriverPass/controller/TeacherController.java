// src/main/java/com/example/DriverPass/controller/TeacherController.java
package com.example.DriverPass.controller;

import com.example.DriverPass.model.User;
import com.example.DriverPass.repository.UserRepository;
import com.example.DriverPass.service.AppointmentService;
import com.example.DriverPass.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final CourseService courseService;
    private final AppointmentService appointmentService;
    private final UserRepository userRepo;

    @GetMapping("/home")
    public String teacherHome(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        if (currentUser != null) {
            User teacher = userRepo.findByEmail(currentUser.getUsername()).orElse(null);
            if (teacher != null) {
                model.addAttribute("teacher", teacher);
                model.addAttribute("courses", courseService.findByTeacher(teacher));
                model.addAttribute("appointments", appointmentService.findByTeacher(teacher));
            }
        }
        return "teacher/home";
    }
}
