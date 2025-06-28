package com.example.DriverPass.controller;

import com.example.DriverPass.dto.VehicleDTO;
import com.example.DriverPass.repository.UserRepository;
import com.example.DriverPass.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepo;
    private final UserRepository userRepo;

    // Display all vehicles
    @GetMapping
    public String showVehicles(Model model) {
        model.addAttribute("vehicles",
                vehicleRepo.findAll().stream().map(VehicleDTO::fromEntity).toList());
        model.addAttribute("users", userRepo.findAll());
        return "admin/vehicles";
    }

    // Add a new vehicle
    @PostMapping("/edit")
    public String editVehicle(@ModelAttribute VehicleDTO dto) {
        vehicleRepo.save(dto.toEntity(userRepo));
        return "redirect:/vehicles";
    }
}
