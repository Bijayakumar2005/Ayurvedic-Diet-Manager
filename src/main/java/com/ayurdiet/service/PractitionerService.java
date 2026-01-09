package com.ayurdiet.service;

import com.ayurdiet.model.Practitioner;

import java.util.List;
import java.util.Optional;

public interface PractitionerService {
    List<Practitioner> findAll();
    Optional<Practitioner> findById(Long id);
    Practitioner save(Practitioner p);
}
