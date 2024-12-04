package com.finstream.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDataDto {

    private double currentPrice;

    private double change;

    private double percentChange;

    private double highPrice;

    private double lowPrice;

    private double openPrice;

    private double previousClosePrice;

    private long timestamp;

    private String state;

    public static String toJson(StockDataDto stockData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(stockData);
        } catch (JsonProcessingException e) {
            log.error("Failed to process StockDataResponse Response: " + e.getMessage());
            return "{}";
        }
    }
}
