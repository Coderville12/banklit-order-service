package com.gp.order.orderservice.service.impl;

import com.gp.order.orderservice.client.CustomerClient;
import com.gp.order.orderservice.dto.OrderRequest;
import com.gp.order.orderservice.dto.OrderResponse;
import com.gp.order.orderservice.entity.Order;
import com.gp.order.orderservice.event.OrderCreatedEvent;
import com.gp.order.orderservice.model.Customer;
import com.gp.order.orderservice.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private  OrderRepo orderRepo;

    @Autowired
    private  CustomerClient customerClient;


    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderServiceImpl(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }



    public OrderResponse createOrder(OrderRequest req) {

        Customer customer = customerClient.getCustomer(req.getCustomerId());

        Order order = Order.builder()
                .products(req.getProduct())
                .quantity(req.getQuantity())
                .amount(req.getAmount())
                .customerId(req.getCustomerId())
                .customer(customer) // WRONG mapping (on purpose)
                .createdAt(LocalDateTime.now())
                .createdBy("system")
                .build();

        Order saved = orderRepo.save(order);

        // Build response
        OrderResponse res = new OrderResponse();
        res.setId(saved.getId());
        res.setProduct(saved.getProducts());
        res.setQuantity(saved.getQuantity());
        res.setAmount(saved.getAmount());
        res.setCustomerId(saved.getCustomerId());
        res.setCustomerName(customer.getName());
        res.setCustomerEmail(customer.getEmail());
        res.setCustomer(customer);
//200 per server //3 * 40 = 120*200= 24000
        Order savedOrder = orderRepo.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getCustomerId(),
                savedOrder.getProducts(),
                savedOrder.getAmount()
        );

        kafkaTemplate.send("order.created", event);
        return res;
    }

    public OrderResponse getOrder(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Customer customer = customerClient.getCustomer(order.getCustomerId());

        OrderResponse res = new OrderResponse();
        res.setId(order.getId());
        res.setProduct(order.getProducts());
        res.setQuantity(order.getQuantity());
        res.setAmount(order.getAmount());
        res.setCustomerId(order.getCustomerId());
        res.setCustomerName(customer.getName());
        res.setCustomerEmail(customer.getEmail());
        res.setCustomer(customer); // WRONG mapping (on purpose)

        return res;
    }
}
