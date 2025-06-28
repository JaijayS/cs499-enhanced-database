package com.example.DriverPass.service;

import com.example.DriverPass.model.Role;
import com.example.DriverPass.model.User;
import com.example.DriverPass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    // Retrieve a user by email
    public Optional<User> getByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    // Retrieve all users, students, and teachers
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Retrieve all students and teachers
    public List<User> getAllStudents() {
        return userRepo.findByRole(Role.ROLE_STUDENT);
    }

    // Retrieve all teachers
    public List<User> getAllTeachers() {
        return userRepo.findByRole(Role.ROLE_TEACHER);
    }

    // Retrieve all admins
    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    // Save a user (create or update)
    public void save(User user) {
        userRepo.save(user);
    }
    // Delete a user by ID
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    // Find a user by email, returning null if not found
    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}
