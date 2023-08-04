package com.example.travelagency.repositories;

import com.example.travelagency.entities.HotelPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelPhotosRepository extends JpaRepository<HotelPhotos, Integer> {
    @Query("SELECT hp.photoImageLink FROM HotelPhotos hp WHERE hp.tours.tourCode = :tourCode")
    List<String> findPhotosLinks(@Param("tourCode") Integer tourCode);

}
