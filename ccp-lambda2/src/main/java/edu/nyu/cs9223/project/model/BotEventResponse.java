package edu.nyu.cs9223.project.model;

import com.amazonaws.services.lexruntime.model.DialogAction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wuweiran
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BotEventResponse {
    private DialogAction dialogAction;

    public BotEventResponse dialogAction(DialogAction dialogAction) {
        setDialogAction(dialogAction);
        return this;
    }
}
