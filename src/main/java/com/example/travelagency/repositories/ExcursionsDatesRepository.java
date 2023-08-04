package com.example.travelagency.repositories;

import com.example.travelagency.entities.ExcursionsDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExcursionsDatesRepository extends JpaRepository<ExcursionsDates, Integer> {

    //[0] - дата начала, [1] - дата окончания
    @Query("SELECT ed.startDate, ed.endDate FROM ExcursionsDates ed WHERE ed.excursions.excursionCode = :excursionCode")
    List<LocalDate[]> findAllExcursionDates(@Param("excursionCode") Integer excursionCode);

}
