package com.example.travelagency.repositories;

import com.example.travelagency.entities.ExcursionPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcursionPhotosRepository extends JpaRepository<ExcursionPhotos, Integer> {

    @Query("SELECT ep.photoImageLink FROM ExcursionPhotos ep WHERE ep.excursions.excursionCode = :excursionCode")
    List<String> findAllExcursionPhotos(@Param("excursionCode") Integer excursionCode);

}
