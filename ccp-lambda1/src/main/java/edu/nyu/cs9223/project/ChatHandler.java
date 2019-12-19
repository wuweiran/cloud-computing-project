package edu.nyu.cs9223.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.nyu.cs9223.project.chatbot.ChatBot;
import edu.nyu.cs9223.project.chatbot.ChatBotFactory;
import edu.nyu.cs9223.project.model.BotRequest;
import edu.nyu.cs9223.project.model.BotResponse;
import edu.nyu.cs9223.project.model.ForbiddenException;
import edu.nyu.cs9223.project.model.InternalServerErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuweiran
 */
public class ChatHandler implements RequestHandler<BotRequest, BotResponse> {
    @Override
    public BotResponse handleRequest(BotRequest botRequest, Context context) {
        final LambdaLogger logger = context.getLogger();
        Map<String, String> headers = new HashMap<>(3);
        headers.put("Content-Type", "application/json");
        logger.log("Request received. Chat Operation: " + botRequest.toString());
        try {
            ChatBot chatBot = ChatBotFactory.getInstance();
            if (botRequest.getOpcode() == BotRequest.OP_MESSAGE) {
                logger.log("Sending message to chat bot: " + botRequest.getMessage());
                List<String> replies = chatBot.postText(botRequest.getUserId(), botRequest.getMessage());
                logger.log("Message sent, response size: " + replies.size());
                return new BotResponse(true, replies);
            } else if (botRequest.getOpcode() == BotRequest.OP_RESET) {
                logger.log("Deleting session of user: " + botRequest.getUserId());
                boolean result = chatBot.resetChat(botRequest.getUserId());
                logger.log("Reset result: " + result);
                if (result) {
                    return new BotResponse(true, null);
                } else {
                    throw new ForbiddenException("forbidden").code(1);
                }
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("internal_server_error:" + e.getMessage()).code(0);
        }
        return null;
    }
}
