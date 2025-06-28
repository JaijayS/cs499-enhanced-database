package com.example.DriverPass.repository;

import com.example.DriverPass.model.Course;
import com.example.DriverPass.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Custom query methods for Course entity
    List<Course> findAll();

    // Find courses by teacher
    List<Course> findByTeacher(User teacher);
}
