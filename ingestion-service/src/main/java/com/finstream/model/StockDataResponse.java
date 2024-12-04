package com.finstream.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockDataResponse {
    // {"c":172.2,"d":0.71,"dp":0.414,"h":172.2082,"l":171.09,"o":171.52,"pc":171.49,"t":1733236379}

    @JsonProperty("c") // Maps "c" in the JSON to currentPrice
    private double currentPrice;

    @JsonProperty("d") // Maps "d" in the JSON to change
    private double change;

    @JsonProperty("dp") // Maps "dp" in the JSON to percentChange
    private double percentChange;

    @JsonProperty("h") // Maps "h" in the JSON to highPrice
    private double highPrice;

    @JsonProperty("l") // Maps "l" in the JSON to lowPrice
    private double lowPrice;

    @JsonProperty("o") // Maps "o" in the JSON to openPrice
    private double openPrice;

    @JsonProperty("pc") // Maps "pc" in the JSON to previousClosePrice
    private double previousClosePrice;

    @JsonProperty("t") // Maps "t" in the JSON to timestamp
    private long timestamp;
}
