package com.example.travelagency.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "excursions")
@Getter
@Setter
public class Excursions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "excursion_code")
    private Integer excursionCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_code", referencedColumnName = "tour_code", nullable = false)
    private Tours tours;

    @Column(name = "excursion_price", nullable = false)
    private Integer excursionPrice;

    @Column(name = "excursion_name", nullable = false)
    @Size(max = 200)
    private String excursionName;

    @Column(name = "excursion_description", nullable = false)
    private String excursionDescription;

}
