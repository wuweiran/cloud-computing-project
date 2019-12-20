package edu.nyu.cs9223.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class SearchRequest implements Serializable {
    String state;
    String city;
    String category;
    Integer rating;
    Integer priceMin;
    Integer priceMax;
    Double latitude;
    Double longitude;
}
