package com.example.DriverPass.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;

    @ManyToOne
    private User student;

    @ManyToOne
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;
}
