package com.example.travelagency.repositories;

import com.example.travelagency.entities.TransportTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportTypesRepository extends JpaRepository<TransportTypes, Integer> {
}
