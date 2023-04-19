package com.example.web.repo;

import com.example.web.models.Immovables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ImmovablesRepository extends JpaRepository<Immovables,Long> {
    List<Immovables> findByTitle(String title);

    List<Immovables> findByTitleContaining( String title);




    List<Immovables> findByTitleContainingAndPriceAfter(String title, Integer minPrice);
    List<Immovables> findByTitleContainingAndPriceBefore(String title, Integer maxPrice);

    List<Immovables> findByTitleContainingAndPriceBetween(String title, Integer minPrice, Integer maxPrice);






}
