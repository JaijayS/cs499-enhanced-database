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

    public Optional<User> getByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public List<User> getAllStudents() {
        return userRepo.findByRole(Role.ROLE_STUDENT);
    }

    public List<User> getAllTeachers() {
        return userRepo.findByRole(Role.ROLE_TEACHER);
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}
