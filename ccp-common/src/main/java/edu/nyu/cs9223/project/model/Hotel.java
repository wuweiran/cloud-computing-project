package edu.nyu.cs9223.project.model;

import com.google.gson.Gson;

public class Hotel {
    public int ID;
    public String name;
    public String address;
    public String city;
    public String state;
    public double latitude, longitude;
    public String postalcode;
    public int rating;
    public int price_low;
    public int price_high;
    public String website;

    public Hotel(int iD, String name, String address, String city, String state, double latitude, double longitude,
                 String postalcode, int rating, int price_low, int price_high, String website) {
        ID = iD;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.postalcode = postalcode;
        this.rating = rating;
        this.price_low = price_low;
        this.price_high = price_high;
        this.website = website;
    }

    public String constructInfo(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

}

