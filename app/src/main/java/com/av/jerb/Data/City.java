package com.av.jerb.Data;

/**
 * Created by Maiada on 10/14/2017.
 */

public class City {
    int id;
    String cityName;

    public City() {

    }

    public City(int id, String cityName) {
        this.id = id;
        this.cityName = cityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
