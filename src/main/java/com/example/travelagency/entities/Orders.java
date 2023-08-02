package com.example.travelagency.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_code")
    private Integer orderCode;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_login", referencedColumnName = "client_login", nullable = false)
    private Clients clients;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_code", referencedColumnName = "tour_code", nullable = false)
    private Tours tours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_type_code", referencedColumnName = "transport_type_code", nullable = false)
    private TransportTypes transportTypes;

}
