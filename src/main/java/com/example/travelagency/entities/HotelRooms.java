package com.example.travelagency.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hotel_rooms")
@Getter
@Setter
public class HotelRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_code")
    private Integer roomCode;

    @Column(name = "room_price_per_day", nullable = false)
    private Integer roomPricePerDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_code", referencedColumnName = "tour_code", nullable = false)
    private Tours tours;

    @Column(name = "room_name", nullable = false)
    @Size(max = 100)
    private String roomName;

    @Column(name = "room_description", nullable = false)
    private String roomDescription;

}
