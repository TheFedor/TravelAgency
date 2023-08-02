package com.example.travelagency.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tours")
@Getter
@Setter
public class Tours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_code")
    private Integer tourCode;

    @Column(name = "tour_name", nullable = false)
    @Size(max = 100)
    private String tourName;

    @Column(name = "tour_short_description", nullable = false)
    private String tourShortDescription;

    @Column(name = "tour_country", nullable = false)
    @Size(max = 200)
    private String tourCountry;

    @Column(name = "hotel_name", nullable = false)
    @Size(max = 100)
    private String hotelName;

    @Column(name = "hotel_description", nullable = false)
    private String hotelDescription;

    @Column(name = "cover_image_link", nullable = false)
    private String coverImageLink;

    @Override
    public String toString() {
        return "Tours{" +
                "tourCode=" + tourCode +
                ", tourName='" + tourName + '\'' +
                ", tourShortDescription='" + tourShortDescription + '\'' +
                ", tourCountry='" + tourCountry + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", hotelDescription='" + hotelDescription + '\'' +
                ", coverImageLink='" + coverImageLink + '\'' +
                '}';
    }
}