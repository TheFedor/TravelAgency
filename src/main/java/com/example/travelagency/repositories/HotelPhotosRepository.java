package com.example.travelagency.repositories;

import com.example.travelagency.entities.HotelPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelPhotosRepository extends JpaRepository<HotelPhotos, Integer> {
}
