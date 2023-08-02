package com.example.travelagency.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hotel_photos")
@Getter
@Setter
public class HotelPhotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_code")
    private Integer photoCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_code", referencedColumnName = "tour_code", nullable = false)
    private Tours tours;

    @Column(name = "photo_image_link", nullable = false)
    @Size(max = 1000)
    private String photoImageLink;

}
