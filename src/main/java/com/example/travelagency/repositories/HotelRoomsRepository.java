package com.example.travelagency.repositories;

import com.example.travelagency.entities.HotelRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRoomsRepository extends JpaRepository<HotelRooms, Integer> {
}
