package edu.nyu.cs9223.project.search;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;
import edu.nyu.cs9223.project.model.Hotel;
import edu.nyu.cs9223.project.model.Spot;

public class DatabaseObject {
    
    
    private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();
    private DynamoDB dynamoDB = new DynamoDB(client);
    
    public void initialDb(){
        DataGetterS3  dataGetter = new DataGetterS3();
        String tableName = "hotel";
        List<Hotel> hotels = dataGetter.getHotelDataFromS3();
        createTable(tableName);
        loadHotelDataToDb(tableName, hotels);
        tableName = "spots";
        List<Spot> spots = dataGetter.getSpotDataFromS3();
        createTable(tableName);
        loadSpotDataToDb(tableName, spots);
    }
    
    public void createTable(String tableName){
        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                Arrays.asList(new KeySchemaElement("ID", KeyType.HASH)),
                Arrays.asList(new AttributeDefinition("ID", ScalarAttributeType.N)),
                new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        }
        catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
    }
    
    public void loadHotelDataToDb(String tableName, List<Hotel> hotels){
        Table table = dynamoDB.getTable(tableName);
        for(Hotel hotel: hotels) {
            try {
                table.putItem(new Item()
                        .withPrimaryKey("ID", hotel.ID)
                        .withJSON("info", hotel.constructInfo()));
                System.out.println("PutItem succeeded: " + hotel.ID);

            }
            catch (Exception e) {
                System.err.println("Unable to add ID: " + hotel.ID);
                System.err.println(e.getMessage());
                break;
            }
       }
    }
    
    public void loadSpotDataToDb(String tableName, List<Spot> spots){
        Table table = dynamoDB.getTable(tableName);
        for(Spot spot: spots) {
            try {
                table.putItem(new Item()
                        .withPrimaryKey("ID", spot.ID)
                        .withJSON("info", spot.constructInfo()));
                System.out.println("PutItem succeeded: " + spot.ID);

            }
            catch (Exception e) {
                System.err.println("Unable to add movie: " + spot.ID);
                System.err.println(e.getMessage());
                break;
            }
       }
    }
    
    public Spot QuerySpot(int id) throws Exception {
        Spot spotRes = null;
        Table table = dynamoDB.getTable("spots");
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#id", "ID");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":xx", id);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#id = :xx").withNameMap(nameMap)
            .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            System.out.println("ID of " + id);
            items = table.query(querySpec);

            iterator = items.iterator();
            Gson g = new Gson();
            
            while (iterator.hasNext()) {
                item = iterator.next();
                String info = item.getJSON("info");
                spotRes = g.fromJson(info, Spot.class);
                System.out.println(item.getNumber("ID") + ": " + item.getJSON("info"));
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query ID of " + id);
            System.err.println(e.getMessage());
        }
        
        return spotRes;
    }
    
    public Hotel QueryHotel(int id) throws Exception {
        Hotel hotelRes = null;
        Table table = dynamoDB.getTable("hotel");
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#id", "ID");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":xx", id);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#id = :xx").withNameMap(nameMap)
            .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            System.out.println("ID of " + id);
            items = table.query(querySpec);

            iterator = items.iterator();
            Gson g = new Gson();
            
            while (iterator.hasNext()) {
                item = iterator.next();
                String info = item.getJSON("info");
                hotelRes = g.fromJson(info, Hotel.class);
                System.out.println(item.getNumber("ID") + ": " + item.getJSON("info"));
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query ID of " + id);
            System.err.println(e.getMessage());
        }
        
        return hotelRes;
    }
    

}
