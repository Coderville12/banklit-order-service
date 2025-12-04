package com.gp.order.orderservice.service.impl;


import com.gp.order.orderservice.dto.OrderRequest;
import com.gp.order.orderservice.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest req);

    OrderResponse getOrder(Long id);
}
