package com.ayurdiet.service.impl;

import com.ayurdiet.model.Practitioner;
import com.ayurdiet.repository.PractitionerRepository;
import com.ayurdiet.service.PractitionerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PractitionerServiceImpl implements PractitionerService {

    private final PractitionerRepository repo;

    public PractitionerServiceImpl(PractitionerRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Practitioner> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Practitioner> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Practitioner save(Practitioner p) {
        return repo.save(p);
    }
}
