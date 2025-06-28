package com.example.DriverPass.controller;

import com.example.DriverPass.dto.CourseDTO;
import com.example.DriverPass.model.User;
import com.example.DriverPass.repository.CourseRepository;
import com.example.DriverPass.repository.UserRepository;
import com.example.DriverPass.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;

    @PostMapping("/enroll")
    public String enrollStudent(@RequestParam Long courseId,
                                @RequestParam Long studentId,
                                Model model) {
        // Enroll student in course
        User student = userRepo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        boolean success = courseService.enrollStudent(student, courseId);

        model.addAttribute(success ? "success" : "error",
                success ? "Student enrolled successfully." : "Student is already enrolled.");

        List<CourseDTO> courses = courseRepo.findAll().stream().map(CourseDTO::fromEntity).toList();
        model.addAttribute("courses", courses);
        return "redirect:/student/home";
    }
}
