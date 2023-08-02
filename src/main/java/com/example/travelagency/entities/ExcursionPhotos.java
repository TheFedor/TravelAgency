package com.example.travelagency.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "excursion_photos")
@Getter
@Setter
public class ExcursionPhotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_code")
    private Integer photoCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excursion_code", referencedColumnName = "excursion_code", nullable = false)
    private Excursions excursions;

    @Column(name = "photo_image_link", nullable = false)
    @Size(max = 1000)
    private String photoImageLink;

}
