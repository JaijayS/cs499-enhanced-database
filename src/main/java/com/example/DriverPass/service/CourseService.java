package com.example.DriverPass.service;

import com.example.DriverPass.model.Course;
import com.example.DriverPass.model.User;
import com.example.DriverPass.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepo;

    public List<Course> findAll() {
        return courseRepo.findAll();
    }

    public List<Course> findByTeacher(User teacher) {
        return courseRepo.findByTeacher(teacher);
    }

    public Course getById(Long id) {
        return courseRepo.findById(id).orElse(null);
    }

    public void save(Course course) {
        courseRepo.save(course);
    }

    public void deleteById(Long id) {
        courseRepo.deleteById(id);
    }

    public boolean enrollStudent(User student, Long courseId) {
        Course course = getById(courseId);
        if (course != null && !course.getStudents().contains(student)) {
            course.getStudents().add(student);
            courseRepo.save(course);
            return true;
        }
        return false;
    }

    public boolean unenrollStudent(User student, Long courseId) {
        Course course = getById(courseId);
        if (course != null && course.getStudents().contains(student)) {
            course.getStudents().remove(student);
            courseRepo.save(course);
            return true;
        }
        return false;
    }

    // Find all courses a student is enrolled in
    public List<Course> findCoursesByStudent(User student) {
        return courseRepo.findAll().stream()
                .filter(course -> course.getStudents().contains(student))
                .collect(Collectors.toList());
    }

    // Find all courses a student is not enrolled in and has not completed
    @Autowired
    private CourseOutcomeService outcomeService;

    // This method returns all courses that a student is not enrolled in and has not completed
    public List<Course> findCoursesNotEnrolledByStudent(User student) {
        return courseRepo.findAll().stream()
                .filter(course -> !course.getStudents().contains(student))
                .filter(course -> !outcomeService.hasCompletedCourse(student, course))
                .collect(Collectors.toList());
    }
}
