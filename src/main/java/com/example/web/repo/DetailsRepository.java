package com.example.web.repo;

import com.example.web.models.Details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsRepository extends JpaRepository<Details,Long> {
}
