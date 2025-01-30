package com.finstream.kafka.producer;

import com.finstream.dto.StockDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, StockDataDto> kafkaTemplate;

    @Value(value = "${kafka.topic.stock-data}")
    private String stockDataTopicName;

    public void sendMessage(StockDataDto stockData) {
        kafkaTemplate.send(stockDataTopicName, String.valueOf(stockData.getTimestamp()), stockData)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Successfully sent: {} to Kafka", stockData);
                    } else {
                        log.error("Unable to send: {} to Kafka due to: {}", stockData, ex.getMessage());
                    }
        });
    }
}
