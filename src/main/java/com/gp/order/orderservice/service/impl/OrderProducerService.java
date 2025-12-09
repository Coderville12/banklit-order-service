package com.gp.order.orderservice.service.impl;

import com.gp.order.orderservice.event.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderProducerService {

    private static final Logger logger = LoggerFactory.getLogger(OrderProducerService.class);

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public OrderProducerService(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(OrderEvent orderEvent) {
        logger.info("Sending order event: {}", orderEvent);

        CompletableFuture<SendResult<String, OrderEvent>> future =
                kafkaTemplate.send(topicName, orderEvent.getOrderId(), orderEvent);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Successfully sent order event [orderId={}] with offset=[{}] to partition=[{}]",
                        orderEvent.getOrderId(),
                        result.getRecordMetadata().offset(),
                        result.getRecordMetadata().partition());
            } else {
                logger.error("Failed to send order event [orderId={}] due to: {}",
                        orderEvent.getOrderId(), ex.getMessage(), ex);
            }
        });
    }

    public void sendOrderEventSync(OrderEvent orderEvent) throws Exception {
        logger.info("Sending order event synchronously: {}", orderEvent);

        SendResult<String, OrderEvent> result =
                kafkaTemplate.send(topicName, orderEvent.getOrderId(), orderEvent).get();

        logger.info("Successfully sent order event [orderId={}] with offset=[{}]",
                orderEvent.getOrderId(),
                result.getRecordMetadata().offset());
    }
}