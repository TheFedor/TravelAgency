package com.example.travelagency.repositories;

import com.example.travelagency.entities.RoomReservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomReservationsRepository extends JpaRepository<RoomReservations, Integer> {
}
