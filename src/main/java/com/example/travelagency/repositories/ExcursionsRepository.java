package com.example.travelagency.repositories;

import com.example.travelagency.entities.Excursions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcursionsRepository extends JpaRepository<Excursions, Integer> {

    @Query("SELECT e.excursionCode FROM Excursions e  WHERE e.tours.tourCode = :tourCode")
    List<Integer> findAllTourExcursionCodes(@Param("tourCode") Integer tourCode);

}
