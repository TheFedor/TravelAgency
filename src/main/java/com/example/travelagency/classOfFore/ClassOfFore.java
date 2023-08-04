package com.example.travelagency.classOfFore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassOfFore {

    private Integer transportTypeCode;
    private String transportType;
    private String transportDescription;
    private Integer transportPrice;

    public ClassOfFore(Integer transportTypeCode, String transportType, String transportDescription, Integer transportPrice) {
        this.transportTypeCode = transportTypeCode;
        this.transportType = transportType;
        this.transportDescription = transportDescription;
        this.transportPrice = transportPrice;
    }

}
