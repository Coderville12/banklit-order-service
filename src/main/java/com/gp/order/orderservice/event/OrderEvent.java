package com.gp.order.orderservice.event;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEvent implements Serializable {

    private String orderId;
    private String customerId;
    private String productName;
    private Integer quantity;
    private Double amount;
    private String status;
    private LocalDateTime orderDate;

    public OrderEvent(Long id, Long customerId, String product, Double amount) {
    }

//    public OrderCreatedEvent() {}
//
//    public OrderCreatedEvent(Long orderId, Long customerId, List<String> items, double totalPrice) {
//        this.orderId = orderId;
//        this.customerId = customerId;
//        this.items = items;
//        this.totalPrice = totalPrice;
//    }

    // getters + setters
}