package edu.nyu.cs9223.project.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author wuweiran
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BotResponse implements Serializable {
    private boolean success;
    private List<String> replies;
}
