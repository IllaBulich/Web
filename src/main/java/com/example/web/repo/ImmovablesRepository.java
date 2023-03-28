package com.example.web.repo;

import com.example.web.models.Immovables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImmovablesRepository extends JpaRepository<Immovables,Long> {
    List<Immovables> findByTitle(String title);

    @Query("SELECT e FROM Immovables e WHERE e.title LIKE %:title%")
    List<Immovables> findByNameContaining(@Param("title") String title);
}
