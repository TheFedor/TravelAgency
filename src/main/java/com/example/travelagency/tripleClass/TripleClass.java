package com.example.travelagency.tripleClass;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TripleClass {

    private List<LocalDate[]> excursionDates;
    private Integer excursionCode;
    private boolean excursionAvailable;

    public TripleClass(List<LocalDate[]> excursionDates, Integer excursionCode, boolean excursionAvailable) {
        this.excursionDates = excursionDates;
        this.excursionCode = excursionCode;
        this.excursionAvailable = excursionAvailable;
    }

}
