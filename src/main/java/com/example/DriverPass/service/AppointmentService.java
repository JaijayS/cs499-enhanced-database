package com.example.DriverPass.service;

import com.example.DriverPass.model.Appointment;
import com.example.DriverPass.model.User;
import com.example.DriverPass.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;

    public void save(Appointment appointment) {
        appointmentRepo.save(appointment);
    }

    public List<Appointment> findAll() {
        return appointmentRepo.findAll();
    }
    public List<Appointment> findByTeacher(User teacher) {
        return appointmentRepo.findByTeacher(teacher);
    }
}
