package com.example.DriverPass.controller;

import com.example.DriverPass.model.Appointment;
import com.example.DriverPass.model.Course;
import com.example.DriverPass.model.User;
import com.example.DriverPass.service.AppointmentService;
import com.example.DriverPass.service.CourseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentAppointmentController {

    private final AppointmentService appointmentService;
    private final CourseService courseService;

    @PostMapping("/schedule/{courseId}")
    public String scheduleAppointment(@PathVariable Long courseId,
                                      @RequestParam("appointmentTime")
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                      LocalDateTime time,
                                      HttpSession session) {

        User student = (User) session.getAttribute("user");
        Course course = courseService.getById(courseId);

        if (student != null && course != null) {
            Appointment appt = new Appointment();
            appt.setStudent(student);
            appt.setCourse(course);
            appt.setTime(time);
            appointmentService.save(appt);
        }

        return "redirect:/student/home";
    }
}
