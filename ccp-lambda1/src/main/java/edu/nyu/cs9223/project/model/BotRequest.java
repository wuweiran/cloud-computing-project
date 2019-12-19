package edu.nyu.cs9223.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author wuweiran
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BotRequest implements Serializable {
    public static final int OP_MESSAGE = 0;
    public static final int OP_RESET = 1;
    private String userId;
    private int opcode;
    private String message;
}
