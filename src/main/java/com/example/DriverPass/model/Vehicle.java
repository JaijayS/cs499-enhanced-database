package com.example.DriverPass.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    private String model;
    private String plateNumber;
    private boolean available;
    private String type;

    private boolean checkedOut;
    private String assignedToName;

    @ManyToOne
    private User assignedTo;
}
