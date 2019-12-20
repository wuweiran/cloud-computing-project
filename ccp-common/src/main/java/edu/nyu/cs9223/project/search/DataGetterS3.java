package edu.nyu.cs9223.project.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import edu.nyu.cs9223.project.model.Hotel;
import edu.nyu.cs9223.project.model.Spot;

public class DataGetterS3 {
    AmazonS3 client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    public List<Spot> getSpotDataFromS3() {
        List<Spot> spots = new ArrayList<>();
        S3Object fileObject = client.getObject("cc-project-files", "tour-revised.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileObject.getObjectContent()));
        String line;
        int count = 0;
        try {
            while((line = reader.readLine()) != null) {
                if(count++ == 0){
                    continue;
                }
                
                //System.out.println(line);
                String[] fields = line.split(",");
                int ID = Integer.valueOf(fields[0]);
                String name = fields[1];
                String city = fields[3];
                String state = fields[2];
                double latitude = Double.valueOf(fields[5]);
                double longitude = Double.valueOf(fields[6]);
                int rating = Integer.valueOf(fields[11]);
                int price_low= Integer.valueOf(fields[13]);
                int price_high= Integer.valueOf(fields[14]);
                if(name.isEmpty() || city.isEmpty() || state.isEmpty()){
                    continue;
                }
                
                Spot spot = new Spot(ID, name, city, state, latitude, longitude,
                rating, price_low, price_high);
                spots.add(spot);
            }
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        return spots;
    }


    public List<Hotel> getHotelDataFromS3() {
        List<Hotel> hotels = new ArrayList<>();
        S3Object fileObject = client.getObject("cc-project-files", "hotel-revised.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileObject.getObjectContent()));
        String line;
        int count = 0;
        try {
            while((line = reader.readLine()) != null) {
                if(count++ == 0){
                    continue;
                }
                
                //System.out.println(line);
                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                int ID = Integer.valueOf(fields[0]);
                String address = fields[1];
                String name = fields[6];
                String city = fields[2];
                String state = fields[8];
                double latitude = Double.valueOf(fields[4]);
                double longitude = Double.valueOf(fields[5]);
                String website = fields[10];
                int rating = Integer.valueOf(fields[9]);
                int price_low= Integer.valueOf(fields[12]);
                int price_high= Integer.valueOf(fields[13]);
                String postalcode= fields[7];
                if(address.isEmpty() || name.isEmpty() || city.isEmpty() || state.isEmpty() || website.isEmpty()){
                    continue;
                }
                
                Hotel hotel = new Hotel(ID, name, address, city, state, latitude, longitude,
                        postalcode, rating, price_low, price_high, website);
                hotels.add(hotel);
            }
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        return hotels;
    }
}
