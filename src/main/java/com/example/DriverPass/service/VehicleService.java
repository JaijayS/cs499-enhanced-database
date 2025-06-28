package com.example.DriverPass.service;

import com.example.DriverPass.model.Vehicle;
import com.example.DriverPass.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    // Service class for managing vehicles
    private final VehicleRepository vehicleRepo;

    // Retrieve all vehicles from the repository
    public List<Vehicle> getAll() {
        return vehicleRepo.findAll();
    }

    // Retrieve a vehicle by its ID
    public Vehicle getVehicleById(Long id) {
        return vehicleRepo.findById(id).orElse(null);
    }

    // Save a vehicle to the repository
    public void save(Vehicle vehicle) {
        vehicleRepo.save(vehicle);
    }


    // Delete a vehicle by its ID
    public void deleteById(Long id) {
        vehicleRepo.deleteById(id);
    }
}
