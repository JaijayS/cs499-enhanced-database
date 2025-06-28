package com.example.DriverPass.repository;

import com.example.DriverPass.model.Course;
import com.example.DriverPass.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAll();
    List<Course> findByTeacher(User teacher);
}
