package com.example.DriverPass.controller;

import com.example.DriverPass.model.User;
import com.example.DriverPass.repository.UserRepository;
import com.example.DriverPass.service.CourseService;
import com.example.DriverPass.service.CourseOutcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final UserRepository userRepo;
    private final CourseService courseService;
    private final CourseOutcomeService outcomeService;

    @GetMapping("/home")
    public String studentHome(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        if (currentUser != null) {
            User student = userRepo.findByEmail(currentUser.getUsername()).orElse(null);
            if (student != null) {
                model.addAttribute("student", student);
                model.addAttribute("enrolledCourses", courseService.findCoursesByStudent(student));
                model.addAttribute("availableCourses", courseService.findCoursesNotEnrolledByStudent(student));
                model.addAttribute("completedCourses", outcomeService.getCompletedCourses(student)); // ✅ THIS LINE FIXES IT
            }
        }
        return "student/home";
    }
}
