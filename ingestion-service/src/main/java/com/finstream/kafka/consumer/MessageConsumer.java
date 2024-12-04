package com.finstream.kafka.consumer;

import com.finstream.dto.StockDataDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class MessageConsumer {

    private final CountDownLatch stockDataLatch = new CountDownLatch(1);

    @KafkaListener(topics = "${kafka.topic.stock-data}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "stockDataKafkaListenerContainerFactory")
    public void stockDataListener(StockDataDto stockDataDto) {
        System.out.println("Received stock data: " + stockDataDto);
        this.stockDataLatch.countDown();
    }
}
