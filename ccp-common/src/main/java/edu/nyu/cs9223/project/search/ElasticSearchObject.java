package edu.nyu.cs9223.project.search;

import edu.nyu.cs9223.project.model.Hotel;
import edu.nyu.cs9223.project.model.Spot;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.http.CorsHandler.Config;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
//import com.amazonaws.http.AWSRequestSigningApacheInterceptor;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.index.query.*;

public class ElasticSearchObject {

    private static String serviceName = "es";
    private static String region = "us-east-1";
    private static String aesEndpoint = "https://search-mynewdomain-cabjbp4ecnoajkr6ol3te4hksq.us-east-1.es.amazonaws.com";
    private static String indexingPathOfSpot = "/spots/_doc";
    private static String indexingPathOfHotel = "/hotels/_doc";

    static final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();
    
    public void initualElasticSearch(){
        DataGetterS3  dataGetter = new DataGetterS3();
        List<Hotel> hotels = dataGetter.getHotelDataFromS3();
        for(Hotel hotel: hotels){
            store(hotel.constructInfo(), hotel.ID, indexingPathOfHotel);
        }

        List<Spot> spots = dataGetter.getSpotDataFromS3();
        for(int i = 4000; i < 8000; i++){
            Spot spot = spots.get(i);
            store(spot.constructInfo(), spot.ID, indexingPathOfSpot);
        }
        
    }

    public void store(String sampleDocument, int id, String indexingPath){
        RestClient esClient = esClient(serviceName, region);
        // Index a document
        HttpEntity entity = new NStringEntity(sampleDocument, ContentType.APPLICATION_JSON);
        Request request = new Request("PUT", indexingPath + "/" + id);
        request.setEntity(entity);        
        Response response;
        try {
            response = esClient.performRequest(request);
            System.out.println(response.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public static RestClient esClient(String serviceName, String region) {
        AWS4Signer signer = new AWS4Signer();
        signer.setServiceName(serviceName);
        signer.setRegionName(region);
        HttpRequestInterceptor interceptor = new AWSRequestSigningApacheInterceptor(serviceName, signer, credentialsProvider);
        return RestClient.builder(HttpHost.create(aesEndpoint)).setHttpClientConfigCallback(hacb -> hacb.addInterceptorLast(interceptor)).build();
    }
    
    private static RestHighLevelClient highLevelEsClient() {
        AWS4Signer signer = new AWS4Signer();
        signer.setServiceName(serviceName);
        signer.setRegionName(region);
        HttpRequestInterceptor interceptor = new AWSRequestSigningApacheInterceptor(serviceName, signer, credentialsProvider);
        return new RestHighLevelClient(RestClient.builder(HttpHost.create(aesEndpoint)).setHttpClientConfigCallback(hacb -> hacb.addInterceptorLast(interceptor)));
    }
    
    public List<Integer> search(String indexName, String city, String state, Double latitude, Double longitude,
            Integer rating, Integer price_low, Integer price_high){
        RestHighLevelClient client = highLevelEsClient();
        //List<SearchRequest> requestList = new ArrayList<>();
        
            SearchRequest searchRequest = new SearchRequest(indexName);   
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQuery = new BoolQueryBuilder();
            if(city != null){
                boolQuery.must(QueryBuilders.matchQuery("city", city));
            }
            if(state != null){
                boolQuery.must(QueryBuilders.matchQuery("state", state));
            }
            if(rating != null){
                boolQuery.must(QueryBuilders.rangeQuery("rating").from(rating));
            }
            if(price_low != null ){
                boolQuery.must(QueryBuilders.rangeQuery("price_high").from(price_low));
            }
            
            if(price_high != null){
                boolQuery.must(QueryBuilders.rangeQuery("price_low").to(price_high));
            }
            
            searchSourceBuilder.query(boolQuery);
            searchRequest.source(searchSourceBuilder);
            SearchResponse response = null;
            try {
                response = client.search(searchRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            List<Integer> result = processResponse(response);
            return result;
    }
    
    public List<Integer> processResponse(SearchResponse response){
        List<Integer> result = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
                JsonObject content = JsonParser.parseString(hit.toString()).getAsJsonObject().getAsJsonObject("_source");
                int id = content.get("ID").getAsInt();
                String sstate= content.get("state").getAsString();
                String sprice_low= content.get("city").getAsString();
                System.out.println(id + " " + sstate + " "+ sprice_low);
                result.add(id);
        }

        return result;
    }
}