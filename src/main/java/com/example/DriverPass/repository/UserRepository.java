package com.example.DriverPass.repository;

import com.example.DriverPass.model.Role;
import com.example.DriverPass.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query to find a user by email
    Optional<User> findByEmail(String email);

    // Custom query to find all users with a specific role
    List<User> findByRole(Role role);
}
