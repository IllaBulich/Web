package com.example.web.repo;

import com.example.web.models.Immovables;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImmovablesRepository extends JpaRepository<Immovables,Long> {
    List<Immovables> findByTitle(String title);
}
