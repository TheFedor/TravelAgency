package com.example.travelagency.repositories;

import com.example.travelagency.entities.OrderExcursions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderExcursionsRepository extends JpaRepository<OrderExcursions, Integer> {
}
