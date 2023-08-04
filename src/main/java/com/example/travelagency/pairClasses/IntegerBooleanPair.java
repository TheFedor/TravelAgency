package com.example.travelagency.pairClasses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntegerBooleanPair {

    private Integer integerValue;
    private boolean booleanValue;
    //true - объект по коду доступен/ false - объект по коду недоступен

    public IntegerBooleanPair(Integer integerValue, boolean booleanValue) {
        this.integerValue = integerValue;
        this.booleanValue = booleanValue;
    }

}
