package com.example.DriverPass.repository;

import com.example.DriverPass.model.Appointment;
import com.example.DriverPass.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByTeacher(User teacher);
}