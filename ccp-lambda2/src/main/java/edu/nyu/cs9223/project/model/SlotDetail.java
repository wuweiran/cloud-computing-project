package edu.nyu.cs9223.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author wuweiran
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SlotDetail {
    private List<Resolution> resolutions;
    private String originalValue;
}
