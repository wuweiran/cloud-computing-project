package edu.nyu.cs9223.project.model;

import com.google.gson.Gson;

import java.io.Serializable;

public class Spot {
    public int ID;
    public String name;
    public String city;
    public String state;
    public double latitude, longitude;
    public int rating;
    public int price_low;
    public int price_high;

    public Spot(int iD, String name, String city, String state, double latitude, double longitude,
                int rating, int price_low, int price_high) {
        ID = iD;
        this.name = name;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.price_low = price_low;
        this.price_high = price_high;
    }

    public String constructInfo(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
