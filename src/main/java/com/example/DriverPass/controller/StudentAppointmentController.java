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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentAppointmentController {

    private final AppointmentService appointmentService;
    private final CourseService courseService;

    @PostMapping("/schedule/{courseId}")
    public String scheduleAppointment(@PathVariable Long courseId,
                                      @RequestParam("appointmentDate")
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                      LocalDate date,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes) {

        User student = (User) session.getAttribute("user");
        Course course = courseService.getById(courseId);

        if (student == null || course == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid student or course.");
            return "redirect:/student/home";
        }

        // Block weekends
        DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            redirectAttributes.addFlashAttribute("error", "Courses can only be scheduled Monday through Friday.");
            return "redirect:/student/home";
        }

        // Set fixed time to 8:00 AM
        LocalDateTime appointmentTime = date.atTime(8, 0);

        // Check course already booked
        List<Appointment> sameCourseDay = appointmentService.findByCourseAndDate(course, appointmentTime);
        if (!sameCourseDay.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "This course is already booked for that day.");
            return "redirect:/student/home";
        }

        // Check if student already booked for the day
        List<Appointment> studentAppts = appointmentService.findByStudent(student);
        boolean hasConflict = studentAppts.stream().anyMatch(
                appt -> appt.getTime().toLocalDate().isEqual(date)
        );
        if (hasConflict) {
            redirectAttributes.addFlashAttribute("error", "You already have a course booked that day.");
            return "redirect:/student/home";
        }

        // Save appointment
        Appointment appt = new Appointment();
        appt.setStudent(student);
        appt.setCourse(course);
        appt.setTime(appointmentTime);
        appointmentService.save(appt);

        redirectAttributes.addFlashAttribute("success", "Course scheduled for " + date + " at 8:00 AM.");
        return "redirect:/student/home";
    }
}
