package edu.nyu.cs9223.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author wuweiran
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CurrentIntent {
    private String name;
    private Map<String, String> slots;
    private Map<String, SlotDetail> slotDetails;
    private String confirmationStatus;
}
