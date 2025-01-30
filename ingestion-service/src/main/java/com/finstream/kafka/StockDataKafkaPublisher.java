package com.finstream.kafka;

import com.finstream.dto.StockDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockDataKafkaPublisher {

    private final KafkaTemplate<String, StockDataDto> kafkaTemplate;

    private final RetryTemplate retryTemplate;

    @Value("${kafka.topic.stock-data}")
    private String stockDataTopic;

    public StockDataKafkaPublisher(KafkaTemplate<String, StockDataDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.retryTemplate = createKafkaRetryTemplate();
    }

    public void publishToKafka(StockDataDto stockData) {
        retryTemplate.execute(context -> {
            kafkaTemplate.send(stockDataTopic, String.valueOf(stockData.getTimestamp()), stockData)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Message failed to send to Kafka: {}", ex.getMessage());
                        } else {
                            log.info("Message sent successfully to Kafka: {}", stockData);
                        }
                    });
            return null;
        }, recoveryContext -> {
            log.error("Retries exhausted. Could not publish message to Kafka for timestamp: {}", stockData.getTimestamp());
            return null;
        });
    }

    private RetryTemplate createKafkaRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(1000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
}
