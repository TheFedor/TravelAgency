package com.example.travelagency.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_excursions")
@Getter
@Setter
public class OrderExcursions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_excursion_code")
    private Integer orderExcursionCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_code", referencedColumnName = "order_code", nullable = false)
    private Orders orders;

    @Column(name = "number_of_tickets", nullable = false)
    private Integer numberOfTickets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excursion_code", referencedColumnName = "excursion_code", nullable = false)
    private Excursions excursions;

}
