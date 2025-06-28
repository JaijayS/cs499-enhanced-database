// src/main/java/com/example/DriverPass/service/CourseOutcomeService.java
package com.example.DriverPass.service;

import com.example.DriverPass.model.Course;
import com.example.DriverPass.model.CourseOutcome;
import com.example.DriverPass.model.User;
import com.example.DriverPass.repository.CourseOutcomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseOutcomeService {

    private final CourseOutcomeRepository outcomeRepo;

    // Save the outcome of a course for a student
    public void saveOutcome(User student, Course course, boolean passed) {
        CourseOutcome outcome = CourseOutcome.builder()
                .student(student)
                .course(course)
                .passed(passed)
                .build();
        outcomeRepo.save(outcome);
    }

    // Check if a student has completed a specific course
    public boolean hasCompletedCourse(User student, Course course) {
        return outcomeRepo.findByStudentAndCourse(student, course).isPresent();
    }

    // Get all completed courses for a student
    public List<CourseOutcome> getCompletedCourses(User student) {
        return outcomeRepo.findByStudent(student);
    }
}
