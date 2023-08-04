package com.example.travelagency.pairClasses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntegerIntegerPair {

    private Integer integerValue1; //room_code
    private Integer integerValue2; //room_price
    //true - объект по коду доступен/ false - объект по коду недоступен

    public IntegerIntegerPair(Integer integerValue1, Integer integerValue2) {
        this.integerValue1 = integerValue1;
        this.integerValue2 = integerValue2;
    }

    @Override
    public String toString() {
        return "(code=" + integerValue1 + ", price=" + integerValue2 + ")";
    }
}
