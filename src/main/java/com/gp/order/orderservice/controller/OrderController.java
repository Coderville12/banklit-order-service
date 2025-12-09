package com.gp.order.orderservice.controller;

import com.gp.order.orderservice.dto.OrderResponse;
import com.gp.order.orderservice.event.OrderEvent;
import com.gp.order.orderservice.service.impl.OrderProducerService;
import com.gp.order.orderservice.service.impl.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService service;

    private OrderProducerService producerService;


    public OrderController(OrderService service, OrderProducerService producerService) {
        this.service = service;
        this.producerService = producerService;
    }

//    @PostMapping
//    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest req) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(req));
//    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishOrder(@RequestBody OrderEvent orderEvent) {

        try {
            if (orderEvent.getOrderId() == null ) {
                String uniqueId= UUID.randomUUID().toString();
                orderEvent.setOrderId(uniqueId);
            }

            if (orderEvent.getStatus() == null) {
                orderEvent.setStatus("CREATED");
            }

            producerService.sendOrderEvent(orderEvent);

            logger.info("Order event published: {}", orderEvent.getOrderId());
            return ResponseEntity.ok("Order event published successfully! OrderId: " + orderEvent.getOrderId());

        } catch (Exception e) {
            logger.error("Error publishing order event", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to publish order event: " + e.getMessage());
        }
    }

    @PostMapping("/publish-sync")
    public ResponseEntity<String> publishOrderSync(@RequestBody OrderEvent orderEvent) {
        try {
            if (orderEvent.getOrderId() == null) {
                String uniqueId= UUID.randomUUID().toString();
                orderEvent.setOrderId(uniqueId);
            }

            if (orderEvent.getStatus() == null) {
                orderEvent.setStatus("CREATED");
            }

            producerService.sendOrderEventSync(orderEvent);

            logger.info("Order event published synchronously: {}", orderEvent.getOrderId());
            return ResponseEntity.ok("Order event published successfully! OrderId: " + orderEvent.getOrderId());

        } catch (Exception e) {
            logger.error("Error publishing order event synchronously", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to publish order event: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> testPublish() {
        OrderEvent testOrder = new OrderEvent();
        testOrder.setOrderId( UUID.randomUUID().toString());
        testOrder.setCustomerId("CUST-" + System.currentTimeMillis());
        testOrder.setProductName("Laptop");
        testOrder.setQuantity(2);
        testOrder.setAmount(2500.00);
        testOrder.setStatus("CREATED");

        producerService.sendOrderEvent(testOrder);

        return ResponseEntity.ok("Test order published! OrderId: " + testOrder.getOrderId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrder(id));
    }
}