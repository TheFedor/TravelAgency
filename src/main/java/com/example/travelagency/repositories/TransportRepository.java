package com.example.travelagency.repositories;

import com.example.travelagency.entities.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {

    @Query("SELECT t.transportPrice FROM Transport t WHERE t.transportTypes.transportTypeCode = :transportTypeCode and t.tours.tourCode = :tourCode")
    List<Integer> findTransportPrice(@Param("transportTypeCode") Integer transportTypeCode, @Param("tourCode") Integer tourCode);

}
