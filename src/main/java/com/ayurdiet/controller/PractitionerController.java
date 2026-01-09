package com.ayurdiet.controller;

import com.ayurdiet.model.Appointment;
import com.ayurdiet.model.Practitioner;
import com.ayurdiet.model.User;
import com.ayurdiet.repository.UserRepository;
import com.ayurdiet.service.AppointmentService;
import com.ayurdiet.service.PractitionerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/practitioners")
public class PractitionerController {

    private final PractitionerService practitionerService;
    private final AppointmentService appointmentService;
    private final UserRepository userRepository; // optional, if you want to link logged-in users

    public PractitionerController(PractitionerService practitionerService,
                                  AppointmentService appointmentService,
                                  UserRepository userRepository) {
        this.practitionerService = practitionerService;
        this.appointmentService = appointmentService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("practitioners", practitionerService.findAll());
        return "practitioners";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Practitioner p = practitionerService.findById(id).orElse(null);
        if (p == null) {
            return "error"; // or throw 404
        }
        model.addAttribute("practitioner", p);
        return "practitioner-view";
    }

    @PostMapping("/{id}/book")
    public String bookAppointment(
            @PathVariable Long id,
            @RequestParam String patientName,
            @RequestParam String patientEmail,
            @RequestParam String patientPhone,
            @RequestParam("appointmentDateTime") String appointmentDateTimeStr,
            @RequestParam(required = false) String notes,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        Practitioner p = practitionerService.findById(id).orElse(null);
        if (p == null) {
            redirectAttributes.addFlashAttribute("error", "Practitioner not found");
            return "redirect:/practitioners";
        }

        Appointment appt = new Appointment();
        appt.setPractitioner(p);
        appt.setPatientName(patientName);
        appt.setPatientEmail(patientEmail);
        appt.setPatientPhone(patientPhone);
        appt.setNotes(notes);

        // parse datetime from HTML datetime-local input (format "yyyy-MM-dd'T'HH:mm")
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime dt = LocalDateTime.parse(appointmentDateTimeStr, fmt);
            appt.setAppointmentDateTime(dt);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Invalid date/time format");
            return "redirect:/practitioners/" + id;
        }

        // optional: link logged-in user
        if (principal != null) {
            // attempt to find user by principal name (adjust to your UserRepository method)
            Optional<User> optUser = userRepository.findByEmail(principal.getName());
            if (!optUser.isPresent()) {
                optUser = userRepository.findByname(principal.getName());
            }
            optUser.ifPresent(appt::setUser);
        }

        appointmentService.save(appt);

        // optional: send email to practitioner/patient (add JavaMailSender, not included here)
        redirectAttributes.addFlashAttribute("success", "Appointment request submitted!");
        return "redirect:/practitioners/" + id + "/success";
    }

    @GetMapping("/{id}/success")
    public String success() {
        return "appointment-success";
    }

    // allow practitioner/admin to view appointments for a practitioner
    @GetMapping("/{id}/appointments")
    public String practitionerAppointments(@PathVariable Long id, Model model) {
        model.addAttribute("appointments", appointmentService.findByPractitionerId(id));
        model.addAttribute("practitioner", practitionerService.findById(id).orElse(null));
        return "practitioner-appointments";
    }
}
