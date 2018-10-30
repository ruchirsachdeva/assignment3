package com.lnu.foundation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rucsac on 15/10/2018.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    @JsonProperty("X")
    private double x;
    @JsonProperty("Y")
    private double y;
    @JsonProperty("time")
    private double time;
    @JsonProperty("button")
    private int button;
    @JsonProperty("correct")
    private int correct;
}
