package com.finstream.service;

import com.finstream.model.StockDataResponse;
import reactor.core.publisher.Mono;

public interface StockDataService {
    Mono<StockDataResponse> fetchStockData();
}
