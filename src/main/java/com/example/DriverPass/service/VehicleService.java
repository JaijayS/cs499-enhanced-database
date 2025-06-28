package com.example.DriverPass.service;

import com.example.DriverPass.model.Vehicle;
import com.example.DriverPass.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepo;

    public List<Vehicle> getAll() {
        return vehicleRepo.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepo.findById(id).orElse(null);
    }

    public void save(Vehicle vehicle) {
        vehicleRepo.save(vehicle);
    }

    public void deleteById(Long id) {
        vehicleRepo.deleteById(id);
    }
}
