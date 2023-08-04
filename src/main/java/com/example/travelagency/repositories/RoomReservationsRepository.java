package com.example.travelagency.repositories;

import com.example.travelagency.entities.RoomReservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomReservationsRepository extends JpaRepository<RoomReservations, Integer> {
    //под нулевым индексом - startDate, под первым - endDate
    @Query("SELECT rr.startDate, rr.endDate FROM RoomReservations rr WHERE rr.hotelRooms.roomCode = :roomCode")
    List<LocalDate[]> findRoomAllStartAndEndReservationsDates(@Param("roomCode") Integer roomCode);

}
