package com.finstream.schedulers;

import com.finstream.dto.StockDataDto;
import com.finstream.kafka.producer.MessageProducer;
import com.finstream.model.StockDataResponse;
import com.finstream.services.StockDataService;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
@AllArgsConstructor
public class StockDataScheduler {

    private final StockDataService stockDataService;

    private MessageProducer messageProducer;

    @PostConstruct
    public void start() {
        Flux.interval(Duration.ofMinutes(1)) // Trigger every minute
                .flatMap(tick -> fetchAndProcessData())
                .doOnNext(data -> log.info("Processed and published data: {}", data))
                .doOnError(error -> log.error("Error during data processing", error))
                .doOnComplete(() -> log.info("Scheduler stopped: All API calls complete."))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

    private Flux<String> fetchAndProcessData() {
        return stockDataService.fetchStockData()
                .flatMapMany(stockDataResponse -> {
                    StockDataDto stockDataTopic = convertStockDataResponseToDto(stockDataResponse);
                    // Publish to Kafka
                    messageProducer.sendMessage(stockDataTopic);
                    return Flux.just(StockDataDto.toJson(stockDataTopic));
                })
                .doOnError(error -> log.error("Error during fetch or publish: {}", error.getMessage()));
    }

    private StockDataDto convertStockDataResponseToDto(StockDataResponse stockDataResponse) {
        return StockDataDto.builder()
                .currentPrice(stockDataResponse.getCurrentPrice())
                .change(stockDataResponse.getChange())
                .percentChange(stockDataResponse.getPercentChange())
                .highPrice(stockDataResponse.getHighPrice())
                .lowPrice(stockDataResponse.getLowPrice())
                .openPrice(stockDataResponse.getOpenPrice())
                .previousClosePrice(stockDataResponse.getPreviousClosePrice())
                .timestamp(stockDataResponse.getTimestamp())
                .state(determineState(stockDataResponse.getTimestamp()))
                .build();
    }

    private String determineState(long timestamp) {
        LocalTime time = Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.of("America/New_York"))
                .toLocalTime();
        return time.isBefore(LocalTime.of(16, 0)) ? "DURING_TRADING" : "AFTER_TRADING";
    }
}
