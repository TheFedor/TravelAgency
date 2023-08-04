package com.example.travelagency.repositories;

import com.example.travelagency.entities.HotelRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRoomsRepository extends JpaRepository<HotelRooms, Integer> {
    @Query("SELECT hr.roomCode FROM HotelRooms hr WHERE hr.tours.tourCode = :tourCode")
    List<Integer> findRoomsByTourCode(@Param("tourCode") Integer tourCode);
}
