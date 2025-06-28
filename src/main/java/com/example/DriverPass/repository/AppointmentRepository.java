package com.example.DriverPass.repository;

import com.example.DriverPass.model.Appointment;
import com.example.DriverPass.model.Course;
import com.example.DriverPass.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Custom query methods for Appointment entity
    List<Appointment> findByTeacher(User teacher);

    // Custom query to find appointments by course
    List<Appointment> findByCourse(Course course);

    // Custom query to find appointments by course and date
    @Query("SELECT a FROM Appointment a WHERE a.course = :course AND FUNCTION('DATE', a.time) = FUNCTION('DATE', :date)")
    List<Appointment> findByCourseAndDate(@Param("course") Course course, @Param("date") LocalDateTime date);

    // Custom query to find appointments by student
    List<Appointment> findByStudent(User student);
}