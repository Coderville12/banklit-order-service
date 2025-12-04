package com.gp.order.orderservice.event;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreatedEvent {

    private Long orderId;
    private Long customerId;
    private List<String> items;
    private double totalPrice;

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