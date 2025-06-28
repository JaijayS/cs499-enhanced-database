package com.example.DriverPass.controller;

import com.example.DriverPass.model.Course;
import com.example.DriverPass.model.Role;
import com.example.DriverPass.model.User;
import com.example.DriverPass.model.Vehicle;
import com.example.DriverPass.repository.CourseRepository;
import com.example.DriverPass.repository.UserRepository;
import com.example.DriverPass.repository.VehicleRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepo;
    private final CourseRepository courseRepo;
    private final VehicleRepository vehicleRepo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/home")
    public String adminDashboard(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        if (currentUser != null) {
            User admin = userRepo.findByEmail(currentUser.getUsername()).orElse(null);
            model.addAttribute("admin", admin);
        }

        model.addAttribute("allUsers", userRepo.findAll());
        model.addAttribute("allCourses", courseRepo.findAll());
        model.addAttribute("allVehicles", vehicleRepo.findAll());
        model.addAttribute("teachers", userRepo.findByRole(Role.ROLE_TEACHER));
        return "admin/home";
    }

    // ====== USERS ======
    @PostMapping("/user/add")
    public String addUser(@RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam Role role) {
        if (userRepo.findByEmail(email).isEmpty()) {
            User user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .build();
            userRepo.save(user);
        }
        return "redirect:/admin/home";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam Long id) {
        userRepo.deleteById(id);
        return "redirect:/admin/home";
    }

    // ====== COURSES ======
    @PostMapping("/course/add")
    public String addCourse(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam Long teacherId) {
        User teacher = userRepo.findById(teacherId).orElse(null);
        if (teacher != null) {
            Course course = Course.builder()
                    .name(name)
                    .description(description)
                    .teacher(teacher)
                    .build();
            courseRepo.save(course);
        }
        return "redirect:/admin/home";
    }

    @PostMapping("/course/delete")
    public String deleteCourse(@RequestParam Long id) {
        courseRepo.deleteById(id);
        return "redirect:/admin/home";
    }

    // ====== VEHICLES ======
    @PostMapping("/vehicle/add")
    public String addVehicle(@RequestParam String make,
                             @RequestParam String model,
                             @RequestParam String plateNumber,
                             @RequestParam(required = false) String assignedToName) {

        Vehicle vehicle = new Vehicle();
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setPlateNumber(plateNumber);
        vehicle.setAvailable(true);
        vehicle.setCheckedOut(false);
        vehicle.setType("Standard");

        if (assignedToName != null && !assignedToName.isBlank()) {
            List<User> users = userRepo.findAll();
            users.stream()
                    .filter(u -> (u.getFirstName() + " " + u.getLastName()).equalsIgnoreCase(assignedToName.trim()))
                    .findFirst()
                    .ifPresent(user -> {
                        vehicle.setAssignedTo(user);
                        vehicle.setAssignedToName(user.getFirstName() + " " + user.getLastName());
                    });
        }

        vehicleRepo.save(vehicle);
        return "redirect:/admin/home";
    }

    @PostMapping("/vehicle/delete")
    public String deleteVehicle(@RequestParam Long id) {
        vehicleRepo.deleteById(id);
        return "redirect:/admin/home";
    }
}
