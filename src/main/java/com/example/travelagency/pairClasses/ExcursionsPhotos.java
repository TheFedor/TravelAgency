package com.example.travelagency.pairClasses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExcursionsPhotos {

    private Integer excursionCode;
    private List<String> excursionPhotos;

    public ExcursionsPhotos(Integer excursionCode, List<String> excursionPhotos) {
        this.excursionCode = excursionCode;
        this.excursionPhotos = excursionPhotos;
    }
}
