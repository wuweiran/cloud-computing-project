package edu.nyu.cs9223.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lexruntime.model.DialogAction;
import com.amazonaws.services.lexruntime.model.DialogActionType;
import com.amazonaws.services.lexruntime.model.FulfillmentState;
import com.google.gson.Gson;
import edu.nyu.cs9223.project.config.Config;
import edu.nyu.cs9223.project.model.BotEvent;
import edu.nyu.cs9223.project.model.BotEventResponse;
import edu.nyu.cs9223.project.model.CurrentIntent;

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
            Map<String, String> slots = intent.getSlots();
            SqsClient sqsClient = SqsClientFactory.getInstance();
            logger.log("Message sending");
            sqsClient.sendMessage(new Gson().toJson(slots));
            logger.log("Message sent");
            return new BotEventResponse().dialogAction(
                    new DialogAction().withType(DialogActionType.Close)
                            .withFulfillmentState(FulfillmentState.Fulfilled));
        } else {
            throw new UnsupportedOperationException("internal_error");
        }
    }
}
