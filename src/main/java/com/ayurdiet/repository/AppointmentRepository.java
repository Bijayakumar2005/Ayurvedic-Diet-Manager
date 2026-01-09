package com.ayurdiet.repository;

import com.ayurdiet.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPractitionerId(Long practitionerId);
    List<Appointment> findByUserId(Long userId);
}
