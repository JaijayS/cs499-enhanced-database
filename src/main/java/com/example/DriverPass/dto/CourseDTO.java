package com.example.DriverPass.dto;

import com.example.DriverPass.model.Course;
import com.example.DriverPass.model.User;
import com.example.DriverPass.model.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseDTO {

    private Long id;
    private String name;
    private String description;
    private String teacherName;
    private String vehicleInfo;
    private int studentCount;

    public static CourseDTO fromEntity(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());

        User teacher = course.getTeacher();
        dto.setTeacherName(teacher != null ? teacher.getFirstName() + " " + teacher.getLastName() : "Unassigned");

        Vehicle vehicle = course.getVehicle();
        dto.setVehicleInfo(vehicle != null ? vehicle.getMake() + " " + vehicle.getModel() : "None");

        dto.setStudentCount(course.getStudents() != null ? course.getStudents().size() : 0);

        return dto;
    }
}
