package com.example.travelagency.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_rooms")
@Getter
@Setter
public class OrderRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_room_code")
    private Integer orderRoomCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_code", referencedColumnName = "order_code", nullable = false)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_code", referencedColumnName = "room_code", nullable = false)
    private HotelRooms hotelRooms;

}
