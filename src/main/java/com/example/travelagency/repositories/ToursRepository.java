package com.example.travelagency.repositories;

import com.example.travelagency.entities.Tours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToursRepository extends JpaRepository<Tours, Integer> {
}
