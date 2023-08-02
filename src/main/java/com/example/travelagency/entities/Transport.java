package com.example.travelagency.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transport")
@Getter
@Setter
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_code")
    private Integer transportCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_type_code", referencedColumnName = "transport_type_code", nullable = false)
    private TransportTypes transportTypes;

    @Column(name = "transport_price", nullable = false)
    private Integer transportPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_code", referencedColumnName = "tour_code", nullable = false)
    private Tours tours;

}
