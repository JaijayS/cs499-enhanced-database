package com.example.DriverPass.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    // A course is taught by one teacher
    @ManyToOne
    private User teacher;

    // Many students can be in many courses
    @ManyToMany
    @JoinTable(
            name = "course_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")  // <-- FIXED HERE
    )
    private Set<User> students = new HashSet<>();
    // One course gets one vehicle
    @OneToOne
    private Vehicle vehicle;

    // Custom constructor used for seeding or quick creation
    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
