package com.example.web.services;

import com.example.web.models.Immovables;
import com.example.web.repo.ImmovablesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImmovablesService {
    private final ImmovablesRepository immovablesRepository;

    public List<Immovables> listImmovables(String title){
        if( title != null ) immovablesRepository.findByTitle(title);
        return immovablesRepository.findAll();
    }

    public void seveImmovables(Immovables immovables){
        log.info("Seving new {}", immovables);
        immovablesRepository.save(immovables);
    }

    public void deleteImmovables(Long id) {
        immovablesRepository.deleteById(id);
    }

    public Immovables getImmovablesById(Long id){
        return immovablesRepository.findById(id).orElse(null);
    }

}
