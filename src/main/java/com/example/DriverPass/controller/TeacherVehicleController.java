package com.example.DriverPass.controller;

import com.example.DriverPass.model.User;
import com.example.DriverPass.model.Vehicle;
import com.example.DriverPass.repository.VehicleRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherVehicleController {

    private final VehicleRepository vehicleRepo;

    @PostMapping("/checkout-vehicle/{vehicleId}")
    public String checkoutVehicle(@PathVariable Long vehicleId, HttpSession session) {
        User teacher = (User) session.getAttribute("user");
        Vehicle vehicle = vehicleRepo.findById(vehicleId).orElse(null);

        if (vehicle != null && teacher != null) {
            vehicle.setCheckedOut(true);
            vehicle.setAssignedToName(teacher.getFirstName() + " " + teacher.getLastName());
            vehicleRepo.save(vehicle);
        }

        return "redirect:/teacher/home";
    }
}
