package com.example.DriverPass.dto;

import com.example.DriverPass.model.Vehicle;
import com.example.DriverPass.repository.UserRepository;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDTO {
    private Long id;
    private String make;
    private String model;
    private String plateNumber;
    private boolean checkedOut;
    private String assignedToName;

    public static VehicleDTO fromEntity(Vehicle vehicle) {
        return VehicleDTO.builder()
                .id(vehicle.getId())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .plateNumber(vehicle.getPlateNumber())
                .checkedOut(vehicle.isCheckedOut())
                .assignedToName(
                        vehicle.getAssignedTo() != null
                                ? vehicle.getAssignedTo().getFirstName() + " " + vehicle.getAssignedTo().getLastName()
                                : null
                )
                .build();
    }
    public Vehicle toEntity(UserRepository userRepo) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(this.id);
        vehicle.setMake(this.make);
        vehicle.setModel(this.model);
        vehicle.setPlateNumber(this.plateNumber);
        vehicle.setCheckedOut(this.checkedOut);
        vehicle.setAssignedToName(this.assignedToName);

        if (this.assignedToName != null) {
            userRepo.findAll().stream()
                    .filter(user -> (user.getFirstName() + " " + user.getLastName()).equals(this.assignedToName))
                    .findFirst()
                    .ifPresent(vehicle::setAssignedTo);
        }

        return vehicle;
    }
}
