//package com.gp.order.orderservice.config;
//
//
//import com.gp.order.orderservice.event.OrderCreatedEvent;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaProducerConfig {
//
//    @Bean
//    public ProducerFactory<String, OrderCreatedEvent> producerFactory() {
//        Map<String, Object> config = new HashMap<>();
//
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//
//        // PRODUCTION SETTINGS
//        config.put(ProducerConfig.ACKS_CONFIG, "all");
//        config.put(ProducerConfig.RETRIES_CONFIG, 10);
//        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15000);
//        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 30000);
//        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);  // exactly-once guarantee
//        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);
//
//        // Throughput settings
//        config.put(ProducerConfig.LINGER_MS_CONFIG, 10);
//        config.put(ProducerConfig.BATCH_SIZE_CONFIG, 32000);
//        config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
//
//        return new DefaultKafkaProducerFactory<>(config);
//    }
//
//    @Bean
//    public KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//}
