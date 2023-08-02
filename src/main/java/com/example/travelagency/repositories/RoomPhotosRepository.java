package com.example.travelagency.repositories;

import com.example.travelagency.entities.RoomPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomPhotosRepository extends JpaRepository<RoomPhotos, Integer> {
}
