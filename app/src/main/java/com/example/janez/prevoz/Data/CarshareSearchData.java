package com.example.janez.prevoz.Data;

import java.io.Serializable;

/**
 * Created by janez on 3. 01. 2016.
 */
public class CarshareSearchData implements Serializable {
    public String fromCity;
    public String toCity;
    public String date;

    public CarshareSearchData(String fromCity, String toCity, String date) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.date = date;
    }
}
