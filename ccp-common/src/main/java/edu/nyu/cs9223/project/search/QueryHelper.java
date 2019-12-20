package edu.nyu.cs9223.project.search;

import edu.nyu.cs9223.project.model.Hotel;
import edu.nyu.cs9223.project.model.Spot;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper {
    ElasticSearchObject elasticSearchObject = new ElasticSearchObject();
    DatabaseObject databaseObject = new DatabaseObject();
    
    public List<Spot> searchForSpot(String city, String state, Double latitude, Double longitude,
                                    Integer rating, Integer price_low, Integer price_high){
        List<Spot> res = new ArrayList<>();
        List<Integer> idList = elasticSearchObject.search("spots", city, state, latitude, longitude, rating, price_low, price_high);
        for(Integer id: idList){
            try {
                Spot spot = databaseObject.QuerySpot(id);
                System.out.print(spot.constructInfo());
                res.add(spot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }
    
    public List<Hotel> searchForHotel(String city, String state, Double latitude, Double longitude,
            Integer rating, Integer price_low, Integer price_high){
        List<Hotel> res = new ArrayList<>();
        List<Integer> idList = elasticSearchObject.search("hotels", city, state, latitude, longitude, rating, price_low, price_high);
        for(Integer id: idList){
            try {
                Hotel hotel = databaseObject.QueryHotel(id);
                res.add(hotel);
                System.out.print(hotel.constructInfo());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
