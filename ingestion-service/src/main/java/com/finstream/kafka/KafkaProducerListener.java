package com.finstream.kafka;

import com.finstream.dto.StockDataDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;

@Slf4j
public class KafkaProducerListener implements ProducerListener<String, StockDataDto> {

    @Override
    public void onSuccess(ProducerRecord<String, StockDataDto> producerRecord, RecordMetadata recordMetadata) {
        log.info("Message sent successfully to topic {} on partition {} at offset {}",
                recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
    }

    @Override
    public void onError(ProducerRecord<String, StockDataDto> producerRecord,
                        RecordMetadata recordMetadata, Exception exception) {
        log.error("Failed to send message to Kafka topic {}: {}", producerRecord.topic(), exception.getMessage());
    }
}
