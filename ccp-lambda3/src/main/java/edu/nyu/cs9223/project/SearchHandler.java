package edu.nyu.cs9223.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import edu.nyu.cs9223.project.model.Hotel;
import edu.nyu.cs9223.project.model.SearchRequest;
import edu.nyu.cs9223.project.model.SearchResponse;
import edu.nyu.cs9223.project.model.Spot;
import edu.nyu.cs9223.project.search.QueryHelper;

import java.util.List;

/**
 * @author wwrus
 */
public class SearchHandler implements RequestHandler<SearchRequest, SearchResponse> {
    @Override
    public SearchResponse handleRequest(SearchRequest searchRequest, Context context) {
        System.out.println(searchRequest.toString());
        QueryHelper queryHelper = new QueryHelper();
        if ("hotel".equalsIgnoreCase(searchRequest.getCategory())) {
            List<Hotel> hotels = queryHelper.searchForHotel(searchRequest.getCity(), searchRequest.getState(),
                    searchRequest.getLatitude(), searchRequest.getLongitude(),
                    searchRequest.getRating(), searchRequest.getPriceMin(),
                    searchRequest.getPriceMax());
            for (Hotel hotel : hotels) {
                System.out.println(hotel.constructInfo());
            }
            return new SearchResponse("hotel", new Gson().toJson(hotels));
        } else if ("spot".equalsIgnoreCase(searchRequest.getCategory())) {
            List<Spot> spots = queryHelper.searchForSpot(searchRequest.getCity(), searchRequest.getState(),
                    searchRequest.getLatitude(), searchRequest.getLongitude(),
                    searchRequest.getRating(), searchRequest.getPriceMin(),
                    searchRequest.getPriceMax());
            for (Spot spot : spots) {
                System.out.println(spot.constructInfo());
            }
            return new SearchResponse("spot", new Gson().toJson(spots));
        }
        return null;
    }
}
