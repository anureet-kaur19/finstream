package com.finstream.services;

import com.finstream.model.StockDataResponse;
import reactor.core.publisher.Mono;

public interface StockDataService {
    Mono<StockDataResponse> fetchStockData();
}
