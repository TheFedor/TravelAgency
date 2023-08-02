package com.example.travelagency.repositories;

import com.example.travelagency.entities.OrderRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRoomsRepository extends JpaRepository<OrderRooms, Integer> {
}
