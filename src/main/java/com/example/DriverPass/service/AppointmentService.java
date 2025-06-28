package com.example.DriverPass.service;

import com.example.DriverPass.model.Appointment;
import com.example.DriverPass.model.Course;
import com.example.DriverPass.model.User;
import com.example.DriverPass.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    // Service class for managing appointments
    private final AppointmentRepository appointmentRepo;

    // Saves an appointment to the database
    public void save(Appointment appointment) {
        appointmentRepo.save(appointment);
    }


    // Deletes an appointment from the database
    public List<Appointment> findAll() {
        return appointmentRepo.findAll();
    }


    // Finds appointments by teacher
    public List<Appointment> findByTeacher(User teacher) {
        return appointmentRepo.findByTeacher(teacher);
    }

    // Finds appointments by course
    public List<Appointment> findByCourseAndDate(Course course, LocalDateTime date) {
        return appointmentRepo.findByCourseAndDate(course, date);
    }

    // Finds appointments by course
    public List<Appointment> findByStudent(User student) {
        return appointmentRepo.findByStudent(student);
    }
}
