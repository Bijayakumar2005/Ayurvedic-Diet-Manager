package com.ayurdiet.service;

import com.ayurdiet.model.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment save(Appointment appointment);
    List<Appointment> findByPractitionerId(Long practitionerId);
    List<Appointment> findAll();
    Optional<Appointment> findById(Long id);
}
