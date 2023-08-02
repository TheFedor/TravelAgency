package com.example.travelagency.repositories;

import com.example.travelagency.entities.Excursions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcursionsRepository extends JpaRepository<Excursions, Integer> {
}
