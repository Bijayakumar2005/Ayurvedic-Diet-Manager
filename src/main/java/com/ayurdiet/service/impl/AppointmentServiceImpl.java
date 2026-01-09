package com.ayurdiet.service.impl;

import com.ayurdiet.model.Appointment;
import com.ayurdiet.repository.AppointmentRepository;
import com.ayurdiet.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repo;

    public AppointmentServiceImpl(AppointmentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Appointment save(Appointment appointment) {
        return repo.save(appointment);
    }

    @Override
    public List<Appointment> findByPractitionerId(Long practitionerId) {
        return repo.findByPractitionerId(practitionerId);
    }

    @Override
    public List<Appointment> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return repo.findById(id);
    }
}
