package com.ayurdiet.repository;

import com.ayurdiet.model.Practitioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PractitionerRepository extends JpaRepository<Practitioner, Long> {
    List<Practitioner> findBySpecializationContainingIgnoreCase(String specialization);
}
