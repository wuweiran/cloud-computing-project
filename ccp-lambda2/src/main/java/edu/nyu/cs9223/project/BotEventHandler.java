package edu.nyu.cs9223.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lexruntime.model.DialogAction;
import com.amazonaws.services.lexruntime.model.DialogActionType;
import com.amazonaws.services.lexruntime.model.FulfillmentState;
import edu.nyu.cs9223.project.config.Config;
import edu.nyu.cs9223.project.model.*;
import edu.nyu.cs9223.project.notif.SnsClient;
import edu.nyu.cs9223.project.notif.SnsClientFactory;
import edu.nyu.cs9223.project.search.QueryHelper;

import java.util.List;
import java.util.Map;

/**
 * @author weiranwu
 */
public class BotEventHandler implements RequestHandler<BotEvent, BotEventResponse> {

    @Override
    public BotEventResponse handleRequest(BotEvent botEvent, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Received bot event");
        CurrentIntent intent = botEvent.getCurrentIntent();
        if (Config.getSearchIntentName().equalsIgnoreCase(intent.getName())) {
            SnsClient snsClient = SnsClientFactory.getInstance();
            Map<String, String> slots = intent.getSlots();
            QueryHelper queryHelper = new QueryHelper();
            if (slots.get("category").equalsIgnoreCase("hotel")) {
                List<Hotel> hotels = queryHelper.searchForHotel(slots.get("city"), slots.get("state"),
                        Double.valueOf(slots.get("latitude")), Double.valueOf(slots.get("longitude")),
                        Integer.valueOf(slots.get("rating")), Integer.valueOf(slots.get("priceMin")),
                        Integer.valueOf(slots.get("priceMax")));
                snsClient.sendSms(convertHotelListToSms(hotels),slots.get("phoneNumber"));
            } else if (slots.get("category").equalsIgnoreCase("spot")) {
                List<Spot> spots = queryHelper.searchForSpot(slots.get("city"), slots.get("state"),
                        Double.valueOf(slots.get("latitude")), Double.valueOf(slots.get("longitude")),
                        Integer.valueOf(slots.get("rating")), Integer.valueOf(slots.get("priceMin")),
                        Integer.valueOf(slots.get("priceMax")));
                snsClient.sendSms(convertSpotListToSms(spots) ,slots.get("phoneNumber"));
            }
            logger.log("Message sent");
            return new BotEventResponse().dialogAction(
                    new DialogAction().withType(DialogActionType.Close)
                            .withFulfillmentState(FulfillmentState.Fulfilled));
        } else {
            throw new UnsupportedOperationException("internal_error");
        }
    }

    private String convertSpotListToSms(List<Spot> spots) {
        StringBuilder sb = new StringBuilder("Spot recommendation :");
        for (Spot spot : spots) {
            sb.append(" - ").append(spot.ID).append(",")
                    .append(spot.city).append(",")
                    .append(spot.latitude).append(",")
                    .append(spot.longitude).append(",")
                    .append(spot.name).append(",")
                    .append(spot.price_high).append(",")
                    .append(spot.price_low).append("\n");
        }
        return sb.toString();
    }

    private String convertHotelListToSms(List<Hotel> hotels) {
        StringBuilder sb = new StringBuilder("Hotel recommendation :");
        for (Hotel hotel : hotels) {
            sb.append(" - ").append(hotel.ID).append(",")
                    .append(hotel.city).append(",")
                    .append(hotel.latitude).append(",")
                    .append(hotel.longitude).append(",")
                    .append(hotel.name).append(",")
                    .append(hotel.price_high).append(",")
                    .append(hotel.price_low).append("\n");
        }
        return sb.toString();
    }
}
