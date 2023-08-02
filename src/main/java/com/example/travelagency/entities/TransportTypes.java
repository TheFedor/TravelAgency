package com.example.travelagency.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transport_types")
@Getter
@Setter
public class TransportTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_type_code")
    private Integer transportTypeCode;

    @Column(name = "transport_type", nullable = false)
    @Size(max = 200)
    private String transportType;

    @Column(name = "transport_description", nullable = false)
    private String transportDescription;

}
