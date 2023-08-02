package com.example.travelagency.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "room_photos")
@Getter
@Setter
public class RoomPhotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_code")
    private Integer photoCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_code", referencedColumnName = "room_code", nullable = false)
    private HotelRooms hotelRooms;

    @Column(name = "photo_image_link", nullable = false)
    @Size(max = 1000)
    private String photoImageLink;

}
