package com.example.travelagency.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "excursions_dates")
@Getter
@Setter
public class ExcursionsDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "excursion_dates_code")
    private Integer excursionDatesCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excursion_code", referencedColumnName = "excursion_code", nullable = false)
    private Excursions excursions;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

}
