package com.finstream.services.impl;

import com.finstream.model.StockDataResponse;
import com.finstream.services.StockDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockDataServiceImpl implements StockDataService {

    private final WebClient.Builder webClientBuilder;

    @Value("${finnhub.key}")
    private String apiKey;

    @Value("${finnhub.base-url}")
    private String baseUrl;

    private static final String SYMBOL = "GOOGL";

    @Override
    public Mono<StockDataResponse> fetchStockData() {
        String url = String.format("/quote?symbol=%s&token=%s", SYMBOL, apiKey);

        return webClientBuilder
                .baseUrl(baseUrl)
                .build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(StockDataResponse.class)
                .doOnError(error -> log.error("Error fetching stock data: {}", error.getMessage(), error));
    }
}
