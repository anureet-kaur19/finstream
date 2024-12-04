package com.finstream.kafka.producer;

import com.finstream.dto.StockDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, StockDataDto> kafkaTemplate;

    @Value(value = "${kafka.topic.stock-data}")
    private String stockDataTopicName;

    public void sendMessage(StockDataDto stockDataDto) {
        kafkaTemplate.send(stockDataTopicName, stockDataDto);
    }

//    public void sendMessage(String message) {
//
//        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
//        future.whenComplete((result, ex) -> {
//
//            if (ex == null) {
//                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata()
//                        .offset() + "]");
//            } else {
//                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
//            }
//        });
//    }
}
