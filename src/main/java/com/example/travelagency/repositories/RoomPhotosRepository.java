package com.example.travelagency.repositories;

import com.example.travelagency.entities.RoomPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomPhotosRepository extends JpaRepository<RoomPhotos, Integer> {

    @Query("SELECT rp.photoImageLink FROM RoomPhotos rp WHERE rp.hotelRooms.roomCode = :roomCode")
    List<String> findPhotosLinks(@Param("roomCode") Integer roomCode);

}
