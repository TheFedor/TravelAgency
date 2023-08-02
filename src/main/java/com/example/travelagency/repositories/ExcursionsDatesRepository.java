package com.example.travelagency.repositories;

import com.example.travelagency.entities.ExcursionsDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcursionsDatesRepository extends JpaRepository<ExcursionsDates, Integer> {
}
