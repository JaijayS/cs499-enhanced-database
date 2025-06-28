// src/main/java/com/example/DriverPass/repository/CourseOutcomeRepository.java
package com.example.DriverPass.repository;

import com.example.DriverPass.model.Course;
import com.example.DriverPass.model.CourseOutcome;
import com.example.DriverPass.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseOutcomeRepository extends JpaRepository<CourseOutcome, Long> {

    // Custom query methods for CourseOutcome entity
    List<CourseOutcome> findByStudent(User student);

    // Find a specific course outcome by student and course
    Optional<CourseOutcome> findByStudentAndCourse(User student, Course course);
}
