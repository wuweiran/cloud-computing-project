package edu.nyu.cs9223.project.model;

import com.amazonaws.services.lexruntime.model.IntentSummary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * DTO chat bot sent to LF1.
 *
 * @author wuweiran
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BotEvent {
    private CurrentIntent currentIntent;
    private Bot bot;
    private String userId;
    private String inputTranscript;
    private String invocationSource;
    private String outputDialogMode;
    private String messageVersion;
    private Map<String, String> sessionAttributes;
    private Map<String, String> requestAttributes;
    private List<IntentSummary> recentIntentSummaryView;
}
